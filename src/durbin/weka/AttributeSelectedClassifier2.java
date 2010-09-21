package durbin.weka;

import weka.classifiers.meta.*;
import weka.attributeSelection.*;

/**
* Extends the default weka AttributeSelectedClassifier in order to expose, what 
* else?, the AttributeSelection.   Why the weka people didn't do this already
* is a mystery. 
*/
public class AttributeSelectedClassifier2 extends AttributeSelectedClassifier{
    
  /***  
  */ 
  public AttributeSelection getAttributeSelection(){
    return(m_AttributeSelection);
  }
  
}