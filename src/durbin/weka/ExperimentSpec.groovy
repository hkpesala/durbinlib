package durbin.weka;

import weka.attributeSelection.*


/**
* Class to hold a single experiment specification. <br><br>
* 
* Currently each line is hard coded with classifier, attributeEval, etc. 
* Should replace with a map from these feature names to their values. 
*/ 
class ExperimentSpec{
   def classifier
   def attributeEval
   def attributeSearch
   def numAttributes
   def classAttribute

	 def discretization
	
	 def dataFile

   // Just to have something handy to print
   def classifierStr
   def attrEvalStr
   def attrSearchStr
   
    //time estimate:   {so batching can divide it up reasonably}

	 def ExperimentSpec(WekaMineResult wmr){
		classifierStr = wmr.classifier
		try{
			if (classifierStr != "None"){
				classifier = WekaMine.classifierFromSpec(classifierStr)
		  }else{
				classifier = null;
			}
			
			attrEvalStr = wmr.attrEval 
		  attributeEval = WekaMine.evalFromSpec(attrEvalStr)  

		  attrSearchStr = wmr.attrSearch
		  attributeSearch = WekaMine.searchFromSpec(attrSearchStr)

		  numAttributes = wmr.numAttrs
		  classAttribute = wmr.classAttr   
			discretization = wmr.discretization
			
			dataFile = wmr.dataFile
			
		}catch(Exception e){
			System.err.println e
			throw(e)
		}						
	}

    
		/***
		* Takes a comma separated string and creates an experiment from it. 
		* headings2Cols gives the index of the column where the given field is derived from
		* this can come from an output heading, or from WekaMineResult.defaultHeadingMap
		* The idea is for all knowledge of the heading order to be confined to WekaMineResult
		* so that we don't have to worry about things getting out of sync. 
		*/ 
   def ExperimentSpec(String line,headings2Cols){
    def fields = line.split(",")

		//System.err.println "DEBUG: "+headings2Cols


    classifierStr = fields[headings2Cols['classifier']]

    try{
      if (classifierStr != "None"){      
        classifier = WekaMine.classifierFromSpec(classifierStr)
      }else{
        classifier = null;
      }
    }catch(Exception e){
      System.err.println "WekaMine.classifierFromSpec failed on:"
      System.err.println classifierStr
      throw(e)
    }
    
    attrEvalStr = fields[headings2Cols['attrEval']] 
    attributeEval = WekaMine.evalFromSpec(attrEvalStr)  
        
    attrSearchStr = fields[headings2Cols['attrSearch']]
    attributeSearch = WekaMine.searchFromSpec(attrSearchStr)
    
    numAttributes = (fields[headings2Cols['numAttrs']] as double) as int
    classAttribute = fields[headings2Cols['classAttr']]     
		discretization = fields[headings2Cols['discretization']]
   }   
   
   String toString(){
     def rstr = attrEvalStr+","+attrSearchStr+","+numAttributes+","+classifierStr+","+classAttribute+","+discretization+","+dataFile
     return(rstr);
   }    
}
