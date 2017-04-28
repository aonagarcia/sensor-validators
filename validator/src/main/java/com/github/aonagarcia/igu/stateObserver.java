/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.aonagarcia.igu;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @author Javier
 */
public class stateObserver {

    private Vector botones;
    private int stateBuildModel = 0;
    private int stateValidateModel = 0;
    private int tabSelected = 0;

    public stateObserver(Vector _ctrls) {
        this.botones = _ctrls;
    }

    public void setStateBuildModel(int s /*,int ts*/) {
        stateBuildModel = s;
        //tabSelected=ts;
        setControlsByState();
    }

    public void setStateValidateModel(int s /*,int ts*/) {
        stateValidateModel = s;
        //tabSelected=ts;
        setControlsByState();
    }

    public void setStateTab(int ts) {
        tabSelected = ts;
        setControlsByState();
    }

    private void setControlsByState() {
        if (tabSelected == 0) {
            switch (stateBuildModel) {
                case 0: {//Estado inicial del sistema
                    String[] enabled = {"m_openCSV","m_buildLocalModel"};
                    String[] disabled = {"m_openCVSRefVals", "m_removeVars", "m_restoreVars", "m_eliminarRegsBttn", "m_deleteDataOutRange",
                        "m_fillMissingData", "listaAlgoritmos", "m_buildModel", "m_viewModel", "m_discretize", "m_DiscretizeConf", "m_saveDiscretizeData",
                        "m_loadData2Validate", "m_validate", "m_stopProcess", "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells","m_showConfParams","m_validateLocalModels"};

                    setEnable2true(enabled);
                    setEnable2false(disabled);
                    break;
                }
                case 1: {//se ha cargado un archivo de datos
                    String[] enabled = {"m_openCSV", "m_openCVSRefVals", "m_removeVars", "m_restoreVars", "m_eliminarRegsBttn", "m_deleteDataOutRange", "m_discretize", "m_DiscretizeConf", "m_markOutliers","m_showConfParams"};
                    String[] disabled = {"m_fillMissingData", "m_buildModel", "m_buildLocalModel","m_viewModel", "m_saveDiscretizeData",
                        "m_loadData2Validate", "m_validate", "m_stopProcess", "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells","m_validateLocalModels"};
                    setEnable2true(enabled);
                    setEnable2false(disabled);
                    break;
                }
                case 2://Variable(s) discretizada(s)
                {

                    String[] enabled = {"m_openCSV", "m_removeVars", "m_restoreVars", "m_eliminarRegsBttn", "m_buildModel", "m_saveDiscretizeData"};
                    String[] disabled = {"m_openCVSRefVals", "m_deleteDataOutRange", "m_fillMissingData", "m_viewModel", "m_buildLocalModel","m_discretize", "m_DiscretizeConf",
                        "m_loadData2Validate", "m_validate", "m_stopProcess", "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells","m_validateLocalModels"};
                    setEnable2true(enabled);
                    setEnable2false(disabled);

                    break;
                }
                case 3: {//Construyendo modelo
                    String[] enabled = {};
                    String[] disabled = {"m_openCSV", "m_openCVSRefVals", "m_removeVars", "m_restoreVars", "m_eliminarRegsBttn", "m_deleteDataOutRange",
                        "m_fillMissingData", "m_buildModel", "m_viewModel","m_buildLocalModel" ,"m_discretize", "m_DiscretizeConf", "m_saveDiscretizeData",
                        "m_loadData2Validate", "m_validate", "m_stopProcess", "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells","m_validateLocalModels"};
                    setEnable2true(enabled);
                    setEnable2false(disabled);
                    break;
                }
                case 4: { //Modelo construido
                    String[] enabled = {"m_openCSV", "m_removeVars", "m_restoreVars", "m_eliminarRegsBttn", "m_fillMissingData",
                        "m_buildModel", "m_buildLocalModel","m_viewModel", "m_saveDiscretizeData", "m_loadData2Validate"};
                    String[] disabled = {"m_openCVSRefVals", "m_deleteDataOutRange", "m_discretize", "m_DiscretizeConf",
                        "m_validate", "m_stopProcess", "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells","m_validateLocalModels"};
                    setEnable2true(enabled);
                    setEnable2false(disabled);
                    break;
                }
            }
        } else if (tabSelected == 1) {
            switch (stateValidateModel) {
                case 4: { //Modelo cargado
                    String[] enabled = {"m_openCSV", "m_viewModel","m_validate","m_validateLocalModels","m_showConfParams"};
                    String[] disabled = {"m_removeVars", "m_restoreVars", "m_eliminarRegsBttn", "m_fillMissingData",
                        "m_buildModel", "m_saveDiscretizeData", "m_loadData2Validate", "m_openCVSRefVals", "m_deleteDataOutRange", "m_discretize", "m_DiscretizeConf",
                         "m_stopProcess", "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells"};
                    setEnable2true(enabled);
                    setEnable2false(disabled);
                    break;
                }
                case 5: {//Datos para validar cargados
                    String[] enabled = {"m_openCSV", "m_removeVars"};
                    String[] disabled = {"m_openCVSRefVals", "m_restoreVars", "m_eliminarRegsBttn", "m_deleteDataOutRange",
                        "m_fillMissingData", "m_buildModel", "m_discretize", "m_DiscretizeConf", "m_saveDiscretizeData",
                        "m_stopProcess", "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells","m_viewModel", "m_validate","m_validateLocalModels"};
                    setEnable2true(enabled);
                    setEnable2false(disabled);
                    break;
                }
                case 6: {//validando Datos
                    String[] enabled = {"m_viewModel", "m_stopProcess"};
                    String[] disabled = {"m_openCSV", "m_openCVSRefVals", "m_removeVars", "m_restoreVars", "m_eliminarRegsBttn", "m_deleteDataOutRange",
                        "m_loadData2Validate", "m_validate", "m_fillMissingData", "m_buildModel", "m_discretize", "m_DiscretizeConf", "m_saveDiscretizeData",
                        "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells"};
                    setEnable2true(enabled);
                    setEnable2false(disabled);

                    break;
                }
                case 7: {//Datos validados
                    String[] enabled = {"m_openCSV", "m_viewModel", "m_loadData2Validate", "m_saveValidatedData", "m_updateModel", "m_applyAll",
                        "m_applyRows", "m_applyCells"};
                    String[] disabled = {"m_openCVSRefVals", "m_removeVars", "m_restoreVars", "m_eliminarRegsBttn", "m_deleteDataOutRange",
                        "m_validate", "m_stopProcess", "m_fillMissingData", "m_buildModel", "m_discretize", "m_DiscretizeConf", "m_saveDiscretizeData"
                    };
                    setEnable2true(enabled);
                    setEnable2false(disabled);
                    break;
                }

                default:
                    break;
            }
        }


    }

    /*  private void setControlsByState_old()
    {
    switch(state)
    {
    case 0:{//Estado inicial del sistema
    String [] enabled={"m_openCSV"};
    String [] disabled={"m_openCVSRefVals","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_deleteDataOutRange",
    "m_fillMissingData","m_buildModel","m_viewModel","m_discretize","m_DiscretizeConf","m_saveDiscretizeData",
    "m_loadData2Validate","m_validate","m_stopProcess","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};

    setEnable2true(enabled);
    setEnable2false(disabled);
    break;}
    case 1:{//se ha cargado un archivo de datos
    String [] enabled={"m_openCSV","m_openCVSRefVals","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_deleteDataOutRange","m_discretize","m_DiscretizeConf"};
    String [] disabled={"m_fillMissingData","m_buildModel","m_viewModel","m_saveDiscretizeData",
    "m_loadData2Validate","m_validate","m_stopProcess","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    setEnable2true(enabled);
    setEnable2false(disabled);

    break;}
    case 2://Variable(s) discretizada(s)
    {

    String [] enabled={"m_openCSV","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_buildModel","m_saveDiscretizeData"};
    String [] disabled={"m_openCVSRefVals","m_deleteDataOutRange","m_fillMissingData","m_viewModel","m_discretize","m_DiscretizeConf",
    "m_loadData2Validate","m_validate","m_stopProcess","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    setEnable2true(enabled);
    setEnable2false(disabled);

    break;}
    case 3:{

    //Construyendo modelo
    String [] enabled={};
    String [] disabled={"m_openCSV","m_openCVSRefVals","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_deleteDataOutRange",
    "m_fillMissingData","m_buildModel","m_viewModel","m_discretize","m_DiscretizeConf","m_saveDiscretizeData",
    "m_loadData2Validate","m_validate","m_stopProcess","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    setEnable2true(enabled);
    setEnable2false(disabled);
    break;}
    case 4:{ //Modelo construido
    if (tabSelected==0)
    {
    String [] enabled={"m_openCSV","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_fillMissingData",
    "m_buildModel","m_viewModel","m_saveDiscretizeData","m_loadData2Validate"};
    String [] disabled={"m_openCVSRefVals","m_deleteDataOutRange","m_discretize","m_DiscretizeConf",
    "m_validate","m_stopProcess","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    setEnable2true(enabled);
    setEnable2false(disabled);
    }

    if (tabSelected==1)
    {
    String [] enabled={"m_openCSV","m_viewModel"};
    String [] disabled={"m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_fillMissingData",
    "m_buildModel","m_saveDiscretizeData","m_loadData2Validate","m_openCVSRefVals","m_deleteDataOutRange","m_discretize","m_DiscretizeConf",
    "m_validate","m_stopProcess","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    setEnable2true(enabled);
    setEnable2false(disabled);
    }

    break;}
    case 5:{//Datos para validar cargados
    if (tabSelected==0)
    {
    String [] enabled={"m_openCSV","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_fillMissingData",
    "m_buildModel","m_viewModel","m_saveDiscretizeData","m_loadData2Validate"};
    String [] disabled={"m_openCVSRefVals","m_deleteDataOutRange","m_discretize","m_DiscretizeConf",
    "m_validate","m_stopProcess","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    setEnable2true(enabled);
    setEnable2false(disabled);
    }
    if (tabSelected==1)
    {
    String [] enabled={"m_openCSV","m_viewModel","m_validate"};
    String [] disabled={"m_openCVSRefVals","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_deleteDataOutRange",
    "m_fillMissingData","m_buildModel","m_discretize","m_DiscretizeConf","m_saveDiscretizeData",
    "m_stopProcess","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    setEnable2true(enabled);
    setEnable2false(disabled);
    }
    break;}
    case 6:{//validando Datos
    if (tabSelected==1)
    {
    String [] enabled={"m_viewModel","m_stopProcess"};
    String [] disabled={"m_openCSV","m_openCVSRefVals","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_deleteDataOutRange",
    "m_loadData2Validate","m_validate","m_fillMissingData","m_buildModel","m_discretize","m_DiscretizeConf","m_saveDiscretizeData",
    "m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    setEnable2true(enabled);
    setEnable2false(disabled);
    }
    break;}
    case 7:{//Datos validados
    if (tabSelected==1)
    {

    }
    if (tabSelected==1)
    {
    String [] enabled={"m_openCSV","m_viewModel","m_loadData2Validate","m_saveValidatedData","m_updateModel","m_applyAll",
    "m_applyRows","m_applyCells"};
    String [] disabled={"m_openCVSRefVals","m_removeVars","m_restoreVars","m_eliminarRegsBttn","m_deleteDataOutRange",
    "m_validate","m_stopProcess","m_fillMissingData","m_buildModel","m_discretize","m_DiscretizeConf","m_saveDiscretizeData"
    };
    setEnable2true(enabled);
    setEnable2false(disabled);
    }
    break;}

    default:break;

    }


    }
     * */
    private void setEnable2true(String[] buttonsNames) {
        for (int i = 0; i < buttonsNames.length; i++) {
            Iterator it = botones.iterator();
            while (it.hasNext()) {
                JButton temp = (JButton) it.next();
                if (temp.getName().equalsIgnoreCase(buttonsNames[i])) {
                    temp.setEnabled(true);
                    break;
                }
            }
        }
    }

    private void setEnable2false(String[] buttonsNames) {
        for (int i = 0; i < buttonsNames.length; i++) {
            Iterator it = botones.iterator();
            while (it.hasNext()) {
                Object O=it.next();
                try {
                    JButton temp = (JButton) O ;
                    if (temp.getName().equalsIgnoreCase(buttonsNames[i])) {
                        temp.setEnabled(false);
                        break;
                    }
                } catch (ClassCastException e) {
                    JComboBox temp = (JComboBox) O;
                    if (temp.getName().equalsIgnoreCase(buttonsNames[i])) {
                        temp.setEnabled(false);
                        break;
                    }
                }

            }
        }
    }
}
