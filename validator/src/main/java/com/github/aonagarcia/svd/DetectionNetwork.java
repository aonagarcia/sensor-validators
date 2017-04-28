/*
 * DetectionNetwork.java
 *
 * Created on 14 de agosto de 2008, 04:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.github.aonagarcia.svd;

import com.github.aonagarcia.extras.Instances;
import com.github.aonagarcia.extras.Utils;
import org.openmarkov.core.exception.CanNotWriteNetworkToFileException;
import org.openmarkov.core.exception.NoFindingException;
import org.openmarkov.core.exception.NotRecognisedNetworkFileExtensionException;
import org.openmarkov.core.exception.ProbNodeNotFoundException;
import org.openmarkov.core.gui.dialog.io.NetsIO;
import org.openmarkov.core.inference.InferenceAlgorithm;
import org.openmarkov.core.io.database.CaseDatabase;
import org.openmarkov.core.model.graph.Graph;
import org.openmarkov.core.model.graph.Node;
import org.openmarkov.core.model.network.*;
import org.openmarkov.core.model.network.potential.TablePotential;
import org.openmarkov.inference.variableElimination.VariableElimination;
import org.openmarkov.io.database.excel.CSVDataBaseIO;
import org.openmarkov.io.probmodel.PGMXReader;
import org.openmarkov.learning.algorithm.pc.PCAlgorithm;
import org.openmarkov.learning.algorithm.pc.independencetester.CrossEntropyIndependenceTester;
import org.openmarkov.learning.core.LearningManager;
import org.openmarkov.learning.core.algorithm.LearningAlgorithm;
import org.openmarkov.learning.core.exception.LatentVariablesException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 *
 * @author Javier
 */
public class DetectionNetwork {

    private ProbNet m_detectionNetwork = null;
    private LearningAlgorithm learningAlgorithm = null;
    protected List<String[]> m_data2;
    protected int m_NumCases = 0;
    protected int m_NumVars = 0;
    //protected HashSet [] m_extendedMarkovBlanquetTable;
    protected double significanceLevel = 0.05;
    protected double alpha = 0.5;
    protected IsolationNetwork IN;
    protected boolean done = false;
    protected double p_value;
    protected double pfth = 0.7;
    protected String m_fileName;

    /*public DetectionNetwork(Domain dn, Instances BD) {
    m_detectionNetwork = dn;
    m_data = BD;
    }*/
    public DetectionNetwork(Instances BD, double sl, double alpha) {
        try {
            CaseDatabase database = InstancesToCaseDatabase(BD);
            LearningManager learningManager = new LearningManager(database, "PC", null, null);
            learningAlgorithm = new PCAlgorithm(learningManager.getLearnedNet(), database, alpha, new CrossEntropyIndependenceTester(), significanceLevel);
            learningManager.init(learningAlgorithm);
            learningManager.learn();
            m_detectionNetwork = learningManager.getLearnedNet();
            m_detectionNetwork.setName("DetectionNetwork");
            done = true;
        } catch (LatentVariablesException e1) {
        } catch (Exception e) {
        }

    }

    public DetectionNetwork(String FileName) {
        this.loadDetectionNetwork(FileName);
        buildIsolationNetwork(pfth);
    }

    private CaseDatabase InstancesToCaseDatabase(Instances inst) {
        ProbNet probNet = new ProbNet();
        List variablesNames = new ArrayList();
        for (int i = 0; i < inst.numAttributes(); i++) {
            variablesNames.add(inst.attribute(i).name());
        }

        int numColumns = variablesNames.size();
        int numRows = inst.numInstances();

        List variablesStatesNames = new ArrayList(numColumns);
        for (int i = 0; i < numColumns; i++) {
            variablesStatesNames.add(new ArrayList());
            Enumeration e = inst.attribute(i).enumerateValues();
            while (e.hasMoreElements()) {
                String stateName = e.nextElement().toString();
                ((List) variablesStatesNames.get(i)).add(stateName);
            }
        }
        probNet = CSVDataBaseIO.getBayesNetVariables("nombreFileAqu�", variablesNames, variablesStatesNames);
        int[][] cases = new int[numRows][numColumns];
        for (int j = 0; j < numRows; j++) {
            for (int i = 0; i < numColumns; i++) {
                cases[j][i] = (int) inst.instance(j).value(i);
            }
        }
        return new CaseDatabase(probNet.getVariables(), cases);
    }

    public boolean builtIt() {
        return done;
    }

    public void setSignificanceLevel(double d) {
        significanceLevel = d;
    }

    public void buildIsolationNetwork(double pfth) {
        IN = new IsolationNetwork();
        IN.setPf_ThValue(pfth);
        IN.buildIsolationNetwork(this);
    }

    public IsolationNetwork getIsolationNetwork() {
        return IN;
    }

    public ProbNet getDomain() {
        return m_detectionNetwork;
    }

    /**
     *Obtiene la Cobija Extendida de Markov .
     *
     *@param El nombre del nodo del que se desea obtener la EMB.
     *@return Conjunto con los nombres de los nodos de la EMB
     */
    public HashSet getExtendedMarkovBlanquet(String nodeName) {
        HashSet EMB = new HashSet();
        int nodeIdx = -1;
        Graph grafo = m_detectionNetwork.getGraph();
        List<Node> nodes = grafo.getNodes();
        List<Node> parents = new ArrayList();
        List<Node> children = new ArrayList();
        List<Node> childrenParents = new ArrayList();

        //buscar el �nidice del nodo "nodeName"
        for (int i = 0; i < nodes.size(); i++) {
            String name = ((ProbNode) nodes.get(i).getObject()).getName();
            if (name.equals(nodeName)) {
                nodeIdx = i;
                //agrega el nodo nodeName dentro del conjunto (cobija de markov)
                EMB.add(name);
                break;
            }
        }

        //Obetner los padres del nodo "nodeName"
        parents = nodes.get(nodeIdx).getParents();
        //Obetner los hijos del nodo "nodeName"
        children = nodes.get(nodeIdx).getChildren();

        /*Obtiene los padres de los hijos del nodo nodeName*/
        ListIterator it = children.listIterator();
        while (it.hasNext()) {
            Node _node = (Node) it.next();
            childrenParents = _node.getParents();

            //agrega al conjunto los padres del hijo del nodo _node
            ListIterator _it = childrenParents.listIterator();
            while (_it.hasNext()) {
                Node _node2 = (Node) _it.next();
                EMB.add(((ProbNode) _node2.getObject()).getName());
            }

        }

        //agrega al conjunto los padres del nodo
        it = parents.listIterator();
        while (it.hasNext()) {
            Node _node = (Node) it.next();
            EMB.add(((ProbNode) _node.getObject()).getName());
        }

        //agrega al conjunto los hijos del nodo
        it = children.listIterator();
        while (it.hasNext()) {
            Node _node = (Node) it.next();
            EMB.add(((ProbNode) _node.getObject()).getName());
        }

        return EMB;
    }

    /**
     *Imprime en la consola la Cobija Extendida de Markov
     */
    protected void printEMB(HashSet c) {

        Iterator emb_it = c.iterator();
        while (emb_it.hasNext()) {
            String n = (String) emb_it.next();
            System.out.print(n + ",");
        }
        System.out.println();
    }

    protected void printNodeBelief(String nodeName, HashMap<Variable, TablePotential> posteriorProbabilities) {
        try {
            double value;
            TablePotential posteriorProbabilitiesPotential = posteriorProbabilities.get(m_detectionNetwork.getVariable(nodeName));
            System.out.print("  " + nodeName + ": ");
            for (int i = 0; i < m_detectionNetwork.getVariable(nodeName).getNumStates(); i++) {
                value = posteriorProbabilitiesPotential.values[i];
                System.out.println(UtilStrings.roundedString(value, "0.001"));
            }
        } catch (ProbNodeNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void saveNet(String fileName) {
        try {
            NetsIO.saveNetworkFile(m_detectionNetwork, fileName + "\\DetectionNetwork.pgmx");
        } catch (NotRecognisedNetworkFileExtensionException ex) {
            ex.printStackTrace();
        } catch (CanNotWriteNetworkToFileException ex) {
            ex.printStackTrace();
        }
    }

    /*    public LabelledDCNode getNode(String name) {
    try {
    LabelledDCNode n = (LabelledDCNode) m_detectionNetwork.getNodeByName(name);
    return n;
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    return null;
    }
     */

    /*public int getNumStates(LabelledDCNode n) {
    try {
    return (int) n.getNumberOfStates();
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    return -1;
    }*/

    /*  public String getStateLabel(LabelledDCNode a, int s) {
    try {
    return a.getStateLabel(s);
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    return null;
    }
     */
    /**Regresa TRUE si la variable est? en la red bayesiana*/
    /*  public boolean existVar(String varName) {
    try {
    LabelledDCNode n = (LabelledDCNode) m_detectionNetwork.getNodeByName(varName);
    if (n != null) {
    return true;
    } else {
    return false;
    }

    } catch (Exception ex) {
    return false;
    }
    }*/
    public void freeMem() {

        m_detectionNetwork = null;

    }

    public int getMaxIndex(double[] a) {
        double maximum = 0;
        int maxIndex = 0;

        for (int i = 0; i < a.length; i++) {
            if ((i == 0) || (a[i] > maximum)) {
                maxIndex = i;
                maximum = a[i];
            }
        }

        return maxIndex;
    }

    /**Regresa el punto medio del intervalo definido en el estado "state" del nodo "a"*/
    public double getMidlePointValue(ProbNode a, int state) {
        Object[] interval = getIntervalStruct(a, state);
        if (interval.length > 2) {
            double menor = Double.valueOf(interval[0].toString());
            double mayor = Double.valueOf(interval[1].toString());
            return menor + ((mayor - menor) / 2);
        } else {
            return Double.valueOf(interval[0].toString());
        }
    }

    protected boolean isNodePresenteInDB(String varName, Instances inst) {
        return inst.attribute(varName) != null ? true : false;
    }

    /**Instancia cada variable del modelo excepto el nodo currentNode*/
    protected void instantiateNodes(ProbNode currentNode, Instances inst, int row, EvidenceCase evidence) {
        try {
            List<ProbNode> _nodeList = m_detectionNetwork.getProbNodes();
            ListIterator it = _nodeList.listIterator();
            while (it.hasNext()) {
                ProbNode _n = (ProbNode) it.next();
                /**Si se trata del nodo actual entonces no instanciar*/
                if (_n.getName().equalsIgnoreCase(currentNode.getName())) {
                    continue;
                } else {
                    if (isNodePresenteInDB(_n.getName(), inst)) {
                        int attIdx = inst.attribute(_n.getName()).index();
                        /*verifica que el dato que se va a instanciar en el nodo no sea "faltante" y no sea "fuera de rango" (en este caso no se podria
                        instanciar un valor en un estado que no existe en el nodo), sin embargo, s? se permite instanciar valores de datos detectados
                        con el tipo de error 3 y 4 (cambio abrupto y error de relacion de variables*/
                        if ((!inst.instance(row).isMissing(attIdx)) && (inst.instance(row).getErrorType(attIdx) != 2)) {
                            /*Obtiene el valor de la base de datos al que se va a instanciar el nodo (caso en el que los datos son continuos)*/
                            double value = inst.instance(row).value(attIdx);
                            /**Obtiene el estado (indice) en el que esta el valor leido*/
                            int state = getIntervalState(_n, value);
                            if (state >= 0) {
                                //n.selectState(state);
                                String labelState = _n.getVariable().getStateName(state);
                                evidence.addFinding(m_detectionNetwork, _n.getName(), labelState);
                            } else {
                                ;//System.out.println("La variable: "+_n.getName()+" no tiene un estado en el cual se encuentre el valor: "+value);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void validar(int row, Instances inst) {
        /**regresa el conjunto af de sensores con "Fallas Aparentes"*/
        HashSet af = getAparentFaultyVars(row, inst);

        /**Dado el conjunto de fallas aparentes af regresa el conjunto de fallas Reales rf*/
        if (af.size() > 0) {
            Vector rf = IN.getRealFaults(af);
            for (int i = 0; i < rf.size(); i++) {
                String varName = (String) rf.get(i);
                if (inst.attribute(varName) != null) {
                    int attIdx = inst.attribute(varName).index();
                    if (inst.instance(row).getErrorType(attIdx) == 0) {
                        inst.instance(row).setErrorType(attIdx, 4);
                        inst.instance(row).setProbableValue(attIdx, Utils.roundDouble(getProbableValue(varName, inst, row, false, 0.3), 8));
                    }
                }
            }
        }

    }

    protected HashSet getAparentFaultyVars(int row, Instances inst) {
        HashSet S = new HashSet();
        boolean contada = false;
        EvidenceCase evidence = new EvidenceCase();
        try {
            /**Se obtiene la lista de nodos de la red de detecci?n*/
            List<ProbNode> _nodeList = m_detectionNetwork.getProbNodes();
            ListIterator it = _nodeList.listIterator();
            /**Se realiza la validaci?n de acuerdo a las variables presentes en la red de detecci?n
             *se recorre la lista y se van validando las variables presentes
             */
            //LabelledDCNode currentNode = (LabelledDCNode)m_detectionNetwork.getNodeByName(currentVar);
            while (it.hasNext()) {

                ProbNode currentNode = (ProbNode) it.next();
                /**Si la variable esta en la base de datos a validar*/
                if ((isNodePresenteInDB(currentNode.getName(), inst)) && (inst.instance(row).getErrorType(inst.attribute(currentNode.getName())) == 0)) {
                    //if ((inst.instance(row).getErrorType(inst.attribute(currentVar))==0)){
                    /**si el nodo se encuentra aislado en la red de detecci?n en cuyo caso no se hace validaci?n*/
                    if (isNodeIsolated(currentNode.getNode()))//No se validan variables aisladas
                    {
                        ;//System.out.println("Variable aislada: "+n.getName());
                    } else /**Si el nodo no esta aislado*/
                    {
                        removeEvidence(evidence);
                        instantiateNodes(currentNode, inst, row, evidence);
                        HashMap<Variable, TablePotential> probs = propagateEvidence(m_detectionNetwork, evidence);
                        //printNodeBelief(currentNode.getName(), probs);
                        int attIdx = inst.attribute(currentNode.getName()).index();
                        double d = inst.instance(row).value(attIdx);
                        int state = getIntervalState(currentNode, d);
                        if (state >= 0) {
                            TablePotential posteriorProbabilitiesPotential = probs.get(currentNode.getVariable());
                            //double P = currentNode.getBelief(state);
                            double P = posteriorProbabilitiesPotential.values[state];
                            //System.out.println(UtilStrings.roundedString(P, "0.001"));
                            if (P >= p_value) {
                                ;
                                //System.out.println("Correct: "+currentNode.getName()+" : "+P);
                                //System.out.println("instancia: "+row+" variable "+currentNode.getName()+" Correct");
                            } else {
                                //System.out.println("Global Aparent Faulty in row: "+row+" var: "+currentNode.getName()+" : "+P);
                                S.add(currentNode.getName());
                            }
                        } else {
                            inst.instance(row).setErrorType(attIdx, 2); // el dato esta fuera de rango
                        }

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return S;
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

    public String getLabelState(String varName, double value) {
        try {
            ProbNode n = (ProbNode) m_detectionNetwork.getProbNode(varName);
            int state = getIntervalState(n, value);
            
            return n.getVariable().getStateName(state);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /*Regresa el indice del estado (intervalo) en el cual se encuentra
     *el valor d
     *@param n El nodo en el que se buscar?
     *@param d El dato a buscar en los intervalos (estados) del nodo
     */
    protected int getIntervalState(ProbNode n, double d) {
        try {
            for (int i = 0; i < n.getVariable().getNumStates(); i++) {
                String s = n.getVariable().getStateName(i);
                StringTokenizer tokens = new StringTokenizer(s, "_");
                int nDatos = tokens.countTokens();
                double[] datos = new double[nDatos];
                int j = 0;
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    datos[j] = Double.valueOf(str).doubleValue();
                    j++;
                }
                //Si es el primer estado se compara d en intervalo [a,b]
                if (i == 0) {//si es el primer estado compara si se trata de un solo valor datos.length==1
                    if (datos.length == 1) {
                        return 0;
                    } else {//si no es un solo valor entonces es un intervalo y d se compara en el intervalo cerrado
                        if ((d >= datos[0]) && (d <= datos[1])) {
                            return 0;
                        }
                    }
                } else { //En cualquier otro estado se compara d en intervalo (a,b]
                    if ((d > datos[0]) && (d <= datos[1])) {
                        return i;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1; //Si d no pertenece a ningun estado regresa -1
    }

    public double getBelief(ProbNode node, EvidenceCase e, HashMap<Variable, TablePotential> probs, int state) {
        TablePotential posteriorProbabilitiesPotential = probs.get(node.getVariable());
        double belief = posteriorProbabilitiesPotential.values[state];
        return belief;
    }

    /**Regresa el valor o intervalo de la variable var con mayor probabilidad sin instanciar variables relacionadas*/
    public Object getMostProbableValue(String var, EvidenceCase e) {
        try {
            this.removeEvidence(e);
            HashMap<Variable, TablePotential> probs = propagateEvidence(m_detectionNetwork, e);
            ProbNode temp = (ProbNode) m_detectionNetwork.getProbNode(var);
            double[] belief = new double[(int) temp.getVariable().getNumStates()];
            for (int i = 0; i < (int) temp.getVariable().getNumStates(); i++) {
                belief[i] = getBelief(temp, e, probs, i);
            }
            int state = getMaxIndex(belief);
            return (double) getMidlePointValue(temp, state);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**Obtiene el valor mas probable para la variable "var" instanciando el resto de los nodos*/
    public Object getMostProbableValue(String var, Instances inst, int row, boolean AsInterval) {
        try {
            EvidenceCase evidence = new EvidenceCase();
            this.removeEvidence(evidence);

            ProbNode n = (ProbNode) m_detectionNetwork.getProbNode(var);
            /**Si el nodo esta aislado se regresa el valor mas probable sin instanciar las variables */
            if (isNodeIsolated(n.getNode())) {
                return getMostProbableValue(var, evidence);
            } else {
                instantiateNodes(n, inst, row, evidence);
                HashMap<Variable, TablePotential> probs = propagateEvidence(m_detectionNetwork, evidence);
                double[] belief = new double[(int) n.getVariable().getNumStates()];
                for (int i = 0; i < (int) n.getVariable().getNumStates(); i++) {
                    //belief[i] = n.getBelief(i);
                    belief[i] = getBelief(n, evidence, probs, i);
                }
                int state = getMaxIndex(belief);
                return (double) getMidlePointValue(n, state);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected boolean isNodeIsolated(Node n) {
        return ((n.getChildren().isEmpty()) && (n.getParents().isEmpty())) ? true : false;
    }

    protected HashMap<Variable, TablePotential> propagateEvidence(ProbNet network, EvidenceCase evidence) {
        try {
            InferenceAlgorithm variableElimination = new VariableElimination(m_detectionNetwork);
            variableElimination.setPreResolutionEvidence(evidence);
            HashMap<Variable, TablePotential> posteriorProbabilities = variableElimination.getProbsAndUtilities();
            return posteriorProbabilities;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void loadDetectionNetwork(String fileNameDN) {
        try {
            m_fileName = fileNameDN;
            InputStream file = new FileInputStream(m_fileName);
            PGMXReader pgmxReader = new PGMXReader();
            m_detectionNetwork = pgmxReader.loadProbNet(file, m_fileName).getProbNet();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setP_Value(double _p, double pfTh) {
        p_value = _p;
        pfth = pfTh;
        if (IN != null) {
            IN.setPf_ThValue(pfTh);
        }
    }

    public boolean isIsolationNetworkNull() {
        return IN == null;
    }

    /**public String [] getIntervals(String varName) {
     * String[] labels=null;
     * try{
     * LabelledDCNode currentNode = (LabelledDCNode)m_detectionNetwork.getNodeByName(varName);
     *
     * labels=new String [currentNode.getNumberOfStates()];
     * for (int i=0; i< currentNode.getNumberOfStates(); i++) {
     * labels[i]=currentNode.getStateLabel(i);
     * }
     *
     * }catch(Exception ex) {
     * ex.printStackTrace();
     * }
     * return labels;
     * }*/

    /*public int getNumNodes() {
    try {
    return m_detectionNetwork.getNodes().size();
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    return -1;
    }*/
    public Object[] getIntervalStruct(ProbNode a, int state) {
        Object[] interval;
        try {

            String s = a.getVariable().getStateName(state);
            StringTokenizer tokens = new StringTokenizer(s, "_");
            int nDatos = tokens.countTokens();
            interval = new Object[nDatos + 1];
            int j = 0;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                interval[j] = Double.valueOf(str).doubleValue();
                j++;
            }
            interval[j] = s;
            return interval;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public LinkedList getVarList() {
        LinkedList l = new LinkedList();
        try {
            List<ProbNode> _nodeList = m_detectionNetwork.getProbNodes();
            ListIterator it = _nodeList.listIterator();
            while (it.hasNext()) {
                ProbNode currentNode = (ProbNode) it.next();
                l.add(currentNode.getName());
            }
            return l;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public double getProbableValue(String varName, Instances inst, int row, boolean nominal, double th) {
        HashSet EMB = getExtendedMarkovBlanquet(varName);
        EMB.remove((Object) varName);
        double value = -1;
        /**Si existen suficientes datos  en la instancia se calcula el dato
         * faltante instanciando los nodos y propagando la evidencia en el modelo*/
        if (getPercentOfMissingData(row, EMB, inst) < th) {//si los datos faltantes en la Cobija de Markov es menor th (0.3){
            value = new Double(getMostProbableValue(varName, inst, row, false).toString()); //se estima instanciando la CM
            inst.instance(row).setMetodoUtilizado(inst.attribute(varName).index(), (short) 1);
        } else /**Si no hay suficiente informaci?n se calcula el dato faltante tomando el dato con mayor probabilidad
         * sin tomar en cuenta las variables relacionadas es decir se utiliza la MODA de acuerdo a los datos
         * aprendidos*/
        {
            //-->           value = new Double(getMostProbableValue(varName, false).toString());//estimado con la MODA
            inst.instance(row).setMetodoUtilizado(inst.attribute(varName).index(), (short) 2);
        }
        return value;
    }

    public double getPercentOfMissingData(int row, HashSet MB, Instances inst) {
        int cont = 0;
        Object[] _MB = MB.toArray();
        for (int i = 0; i < _MB.length; i++) {
            if (inst.attribute((String) _MB[i]) != null) {
                if (inst.instance(row).isMissing(inst.attribute((String) _MB[i]).index())) {
                    cont++;
                }
            } else {
                cont++;
            }
        }
        return (double) cont / (double) _MB.length;
    }

//    public void updateModel(Instances newInst) {
//        try {
//
//            NodeList _nodeList = m_detectionNetwork.getNodes();
//            ListIterator it = _nodeList.listIterator();
//            for (int row = 0; row < newInst.numInstances(); row++) {
//                while (it.hasNext()) {
//                    LabelledDCNode currentNode = (LabelledDCNode) it.next();
//                    instantiateNodes(currentNode, newInst, row);
//                    propagateEvidence(m_detectionNetwork);
//                    m_detectionNetwork.adapt();
//                }
//            }
//
//            //2011 m_detectionNetwork.saveAsKB(m_fileName);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
    public Vector getBayesNetStructure() {
        Vector nodos = new Vector();
        try {
            List<ProbNode> _nodeList = m_detectionNetwork.getProbNodes();
            ListIterator itNode = _nodeList.listIterator();
            while (itNode.hasNext()) {
                ProbNode currentNode = ((ProbNode) itNode.next());
                List<Node> children = currentNode.getNode().getChildren();
                //Se crea la lista y se agrega el nodo al inicio de la lista
                LinkedList adyacencia = new LinkedList();
                adyacencia.add(currentNode.getName());
                //Se obtienen los padres del nodo y se agregan a la lista
                ListIterator itChildren = children.listIterator();
                while (itChildren.hasNext()) {
                    Node childrenNode = ((Node) itChildren.next());
                    adyacencia.add(((ProbNode) childrenNode.getObject()).getName());
                }
                //Al terminar, se agrega la lista de adyacencia al vector
                nodos.add(adyacencia);
            }
            return nodos;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<ProbNode> getNodes() {
        return this.m_detectionNetwork.getProbNodes();
    }
}
