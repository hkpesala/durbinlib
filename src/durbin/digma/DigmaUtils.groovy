
import durbin.util.*


/************************************************************
* Some utilities to help process inputs and outputs from/to
* Digma (a bayesian factor graph for integrating genomic data
* into biological pathway models).
* 
*/
class DigmaUtils{

  /**************************************
  * Sometimes I get clinical features as a flat list.  Format:
  *
  * name  sample_id value
  * 
  * return: map from sample ID's to clinical value. 
  */ 
  static Map readClinicalFromList(clinicalFile,featureName){  
    // Read in the clinical feature we're interested in...
    //$clinicalName.... 
    System.err.print "Reading attribute $featureName from file $clinicalFile..."
    def clinicalVal = [:]
    new File(clinicalFile).withReader{r->
      // name    sample_id       val
      def header = r.readLine();
      r.splitEachLine(/\s/){fields->
        if (fields[0] == featureName){
          //id = "sample_"+fields[1]
          def id = fields[1]
          clinicalVal[id] = fields[2] as Double
        }
      }  
    }
    System.err.println "done."
    return(clinicalVal)
  }  
  
  /*****************************************
  * Sometimes I get clinical features as a table.
  * 
  * rows are features, columns are patient IDs
  * 
  */
  static Map readClinicalFromTable(clinicalFile,featureName){
    def clinicalVal = [:]
    def clinicalTable = new Table(clinicalFile);
    def attributeRow = clinicalTable.getRowIdx(featureName)
    if (attributeRow == null){
      System.err.println "ERROR, no attribute named $featureName in table. "
      System.err.println clinicalTable.rowNames()
    }
        
    (0..<clinicalTable.numCols).each{col->
      def val = clinicalTable[attributeRow][col]
      def id = clinicalTable.colNames[col]
      
      // Check for missing clinical values... just skip those, what else can you do?
      if ((val != "") && (val != null)){
        clinicalVal[id] = val
      }
    }  
    return(clinicalVal)        
  }

}