/*
 * IsolationNetwork.java
 *
 * Created on 23 de octubre de 2008, 03:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import java.util.ListIterator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openmarkov.core.exception.CanNotWriteNetworkToFileException;
import org.openmarkov.core.exception.IncompatibleEvidenceException;
import org.openmarkov.core.exception.InvalidStateException;
import org.openmarkov.core.exception.NoFindingException;
import org.openmarkov.core.exception.NotRecognisedNetworkFileExtensionException;
import org.openmarkov.core.exception.ProbNodeNotFoundException;
import org.openmarkov.core.gui.dialog.io.NetsIO;
import org.openmarkov.core.inference.InferenceAlgorithm;
import org.openmarkov.core.model.graph.Node;
import org.openmarkov.core.model.network.EvidenceCase;
import org.openmarkov.core.model.network.Finding;
import org.openmarkov.core.model.network.NodeType;
import org.openmarkov.core.model.network.ProbNet;
import org.openmarkov.core.model.network.ProbNode;
import org.openmarkov.core.model.network.State;
import org.openmarkov.core.model.network.Variable;
import org.openmarkov.core.model.network.potential.Potential;
import org.openmarkov.core.model.network.potential.PotentialRole;
import org.openmarkov.core.model.network.potential.TablePotential;
import org.openmarkov.inference.variableElimination.VariableElimination;

/**
 *
 * @author Javier
 */
public class IsolationNetwork {

    protected String saveDirectory = ".\\";
    protected ProbNet m_isolationNetwork;
    //protected BooleanDCNode[] m_Nodes;
    protected List<ProbNode> m_NodeList;
    protected double q = 0.01;
    protected Object[][] P_f;
    protected double Pf_Th;

    /** Creates a new instance of IsolationNetwork */
    public IsolationNetwork() {
    }

    protected void buildIsolationNetwork(DetectionNetwork detectionNetwork) {
        try {
            m_isolationNetwork = new ProbNet();
            m_NodeList = new ArrayList();

            /*Lista de nodos temporal para obtener los nodos de la red de detectionNetwork*/
            List<ProbNode> Temp = detectionNetwork.getNodes();

            /*Crea los nodos de la red isolationNetwork*/
            for (int i = 0; i < Temp.size(); i++) {
                ProbNode n = (ProbNode) Temp.get(i);
                State[] states = {new State("TRUE"), new State("FALSE")};
                /*Crea un nuevo nodo para las fallas reales*/
                ProbNode r = new ProbNode(m_isolationNetwork, new Variable("R_" + n.getName(), states), NodeType.CHANCE);
                /*Crea un nuevo nodo para las fallas aparentes*/
                ProbNode a = new ProbNode(m_isolationNetwork, new Variable("A_" + n.getName(), states), NodeType.CHANCE);
                m_NodeList.add(r);
                m_NodeList.add(a);
                m_isolationNetwork.addProbNode(r);
                m_isolationNetwork.addProbNode(a);
            }
            

            for (int i = 0; i < Temp.size(); i++) {
                /*Obtiene el nodo A_name al cual se definiran sus relaciones*/
                String Aname = ((ProbNode) Temp.get(i)).getName();
                ProbNode n = m_isolationNetwork.getProbNode("A_" + Aname);
                HashSet EMB = detectionNetwork.getExtendedMarkovBlanquet(Aname);

                /*Crea las relaciones definidas por la cobija de Markov*/
                Iterator emb_it = EMB.iterator();
                while (emb_it.hasNext()) {
                    String Rname = (String) emb_it.next();
                    ProbNode n2 = m_isolationNetwork.getProbNode("R_" + Rname);
                    m_isolationNetwork.addLink(n2, n, true);
                }
            }

            /**Crea la CPT de los nodos "Falla Aparente"*/
            /*La CPT de los nodos "Falla real" es 0.5 por default para cada estado*/
            for (int i = 0; i < Temp.size(); i++) {
                String Nname = ((ProbNode) Temp.get(i)).getName();
                ProbNode r_n = m_isolationNetwork.getProbNode("R_" + Nname);
                r_n.setPotential(new TablePotential(PotentialRole.CONDITIONAL_PROBABILITY,r_n.getVariable()));
                ProbNode a_n = m_isolationNetwork.getProbNode("A_" + Nname);
                fillCPT(a_n);

            }
            P_f = new Object[2][m_isolationNetwork.getNumNodes() / 2];

            this.saveIsolationNetwork("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**Realiza la multiplicaci�n del Noisy OR
     *En los casos en los que en la configuraci�n haya un valor verdadero (1)
     *se multiplica el valor q;
     *@param La configuraci�n de estados de los nodos
     */
    protected double MultQ(int[]/*long[]*/ conf) {
        double prob = 1.0;
        /**No se compara el �ltimo elemento del arreglo ya que los valores pertenecen
         *a los estados del nodo de Falla Aparente (A_name)
         *P(A_name|R1,R2,...,Rn)
         */
        //for (int i = 0; i < conf.length - 1; i++) {
        for (int i = 1; i < conf.length ; i++) {
            if (conf[i] == 0) { //solo para cuando d es verdadero (=1)
                prob = prob * q;
            }
        }

        return prob;

    }

    public void saveIsolationNetwork(String bayesNetworkName) {
        try {
            NetsIO.saveNetworkFile(m_isolationNetwork,"D:\\IN.pgmx");
        } catch (NotRecognisedNetworkFileExtensionException ex) {
        } catch (CanNotWriteNetworkToFileException ex) {
        }
    }

    protected double computeProbability(int/*long*/[] conf) {
        //El primer(em open markov) (�ltimo en hugin) elemento del arreglo corresponde a la configuraci�n del nodo de falla aparente 1=false, 0=true
        //si este valor es falso se refiere a calcular P(~m | d1,d2,...) = q11*q21*.... solo para cundo d = 1
        //Si es igual a 1 entonces se calcula P(m | d1,d2, d3)= 1- q11*q21*....
        //if (conf[conf.length - 1] == 0) {
        if (conf[0] == 1) {
            return MultQ(conf);
        } else {
            return 1 - MultQ(conf);
        }
    }

    private List<Variable> getVarListNode(ProbNode n) {
        List<Variable> varlist = new ArrayList();
        List<Node> parents = new ArrayList();

        varlist.add(n.getVariable());

        parents = n.getNode().getParents();
        ListIterator it = parents.listIterator();
        while (it.hasNext()) {
            Node _node = (Node) it.next();
            varlist.add(((ProbNode) _node.getObject()).getVariable());
        }
        return varlist;
    }

    public void fillCPT(ProbNode n) {
        List<Variable> varlist = getVarListNode(n);
        TablePotential potencial = new TablePotential(varlist, PotentialRole.CONDITIONAL_PROBABILITY);       

        /**Crea arreglo para almacenar las configuraciones del espacio de estados de los nodos*/
        int[] configuracion;
        /**En este ciclo se obtienen las configuraciones para cada �ndice de la tabla*/
        for (int i = 0; i < potencial.getTableSize(); i++) {
            /**Obtiene la configuraci�n de cada elemento de la CPT*/
            configuracion = getConfiguration(varlist.size(), i);

            /**Asigna la probabilidad calculada para la configuraci�n dada*/
            potencial.values[i] = computeProbability(configuracion);
        }
        n.setPotential(potencial);
    }

    public int[] getConfiguration(int k, int input) {
        boolean[] bits = new boolean[k];
        int[] configuration = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            bits[i] = (input & (1 << i)) != 0;
        }
        for (int i = 0; i < k; i++) {
            configuration[i] = bits[i] ? 1 : 0;
        }
        return configuration;
    }

    public void freeMem() {
        m_isolationNetwork = null;
    }

    public Vector getRealFaults(HashSet s) {
        Vector realFaultyVars = new Vector();
        EvidenceCase evidence = new EvidenceCase();

        realFaultyVars.clear();

        //instanciarNodosIN(s);
        //propagateEvidence(m_isolationNetwork);

        removeEvidence(evidence);
        instanciarNodosIN(s,evidence);
        
        HashMap<Variable, TablePotential> probs = propagateEvidence(m_isolationNetwork, evidence);
        updatePFvector(probs);
        for (int i = 0; i < P_f[1].length; i++) {
            if (Double.valueOf(P_f[1][i].toString()).doubleValue() >= Pf_Th) {
                String _name = (String) P_f[0][i];
                realFaultyVars.add(_name.substring(2)); //almacena el nombre de la variable con "falla real" (eliminando del nombre el prefijo "R_")

            }
        }
        return realFaultyVars;
    }

    protected HashMap<Variable, TablePotential> propagateEvidence(ProbNet network, EvidenceCase evidence) {
        try {
            InferenceAlgorithm variableElimination = new VariableElimination(network);
            variableElimination.setPreResolutionEvidence(evidence);
            HashMap<Variable, TablePotential> posteriorProbabilities = variableElimination.getProbsAndUtilities();
            return posteriorProbabilities;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    

    protected void updatePFvector(HashMap<Variable, TablePotential> probs) {
        try {
            int j = 0;
            List<ProbNode> nl = m_isolationNetwork.getProbNodes();
            for (int i = 0; i < nl.size(); i++) {
                ProbNode _nn = (ProbNode) nl.get(i);
                if (_nn.getName().charAt(0) == 'R') {
                    P_f[0][j] = (Object) _nn.getName();

                    TablePotential posteriorProbabilitiesPotential = probs.get(_nn.getVariable());
                    P_f[1][j] = posteriorProbabilitiesPotential.values[0];
                    //P_f[1][j] = (Object) _nn.getBelief(1);
                    j++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**Instanc�a los nodos de la red de aislamiento de acuerdo
     *a las variables detectadas como fallas aparentes almacenadas
     *en el conjunto s
     */
    protected void instanciarNodosIN(HashSet s, EvidenceCase evidence) {
        try {
            List<ProbNode> nl = m_isolationNetwork.getProbNodes();
            ListIterator it = nl.listIterator();
            while (it.hasNext()) {

                ProbNode n = (ProbNode) it.next();
                if (n.getName().startsWith("A"))
                    if(isInSet(n.getName(),s))
                        evidence.addFinding(m_isolationNetwork, n.getName(), "TRUE");
                    else
                        evidence.addFinding(m_isolationNetwork, n.getName(), "FALSE");
            }            
        } catch (ProbNodeNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidStateException ex) {
            ex.printStackTrace();
        } catch (IncompatibleEvidenceException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isInSet(String node,HashSet set)
    {
        Iterator it_s = set.iterator();
            while (it_s.hasNext()) {
                String temp=(String) it_s.next();
                if (node.equalsIgnoreCase("A_"+temp))
                    return true;
            }
        return false;
    }

    public void setPf_ThValue(double pfTh) {
        Pf_Th = pfTh;
    }

    public double getPf_ThValue() {
        return Pf_Th;
    }

    protected void removeEvidence(EvidenceCase e) {
        ListIterator it = e.getFindings().listIterator();
        while (it.hasNext()) {
            try {
                Finding finding = (Finding) it.next();
                e.removeFinding(finding.getVariable());
            } catch (NoFindingException ex) {
                ex.printStackTrace();
            }
        }
    }
}
