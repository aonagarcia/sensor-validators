/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    DecisionTable.java
 *    Copyright (C) 1999 Mark Hall
 *
 */

package com.github.aonagarcia.gaussianmixturemodel;
import java.io.*;
import java.util.*;
import com.github.aonagarcia.extras.*;
/*
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.IB1;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
 *
 */

/**
 * Class for building and using a simple decision table majority classifier.
 * For more information see: <p>
 *
 * Kohavi R. (1995).<i> The Power of Decision Tables.</i> In Proc
 * European Conference on Machine Learning.<p>
 *
 * Valid options are: <p>
 *
 * -S num <br>
 * Number of fully expanded non improving subsets to consider
 * before terminating a best first search.
 * (Default = 5) <p>
 *
 * -X num <br>
 * Use cross validation to evaluate features. Use number of folds = 1 for
 * leave one out CV. (Default = leave one out CV) <p>
 * 
 * -I <br>
 * Use nearest neighbour instead of global table majority. <p>
 *
 * -R <br>
 * Prints the decision table. <p>
 *
 * @author Mark Hall (mhall@cs.waikato.ac.nz)
 * @version $Revision: 1.29.2.1 $ 
 */
public class DecisionTable extends Classifier 
  implements OptionHandler, WeightedInstancesHandler, 
	     AdditionalMeasureProducer {

    @Override
    public void buildClassifier(Instances data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration enumerateMeasures() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getMeasure(String measureName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
   public static class hashKey implements Serializable {

    /** Array of attribute values for an instance */
    private double [] attributes;

    /** True for an index if the corresponding attribute value is missing. */
    private boolean [] missing;

    /** The values */
    private String [] values;

    /** The key */
    private int key;

    /**
     * Constructor for a hashKey
     *
     * @param t an instance from which to generate a key
     * @param numAtts the number of attributes
     * @param ignoreClass if true treat the class as a normal attribute
     */
    public hashKey(Instance t, int numAtts, boolean ignoreClass) throws Exception {

      int i;
      int cindex = t.classIndex();

      key = -999;
      attributes = new double [numAtts];
      missing = new boolean [numAtts];
      for (i=0;i<numAtts;i++) {
	if (i == cindex && !ignoreClass) {
	  missing[i] = true;
	} else {
	  if ((missing[i] = t.isMissing(i)) == false) {
	    attributes[i] = t.value(i);
	  }
	}
      }
    }

    /**
     * Convert a hash entry to a string
     *
     * @param t the set of instances
     * @param maxColWidth width to make the fields
     */
    public String toString(Instances t, int maxColWidth) {

      int i;
      int cindex = t.classIndex();
      StringBuffer text = new StringBuffer();

      for (i=0;i<attributes.length;i++) {
	if (i != cindex) {
	  if (missing[i]) {
	    text.append("?");
	    for (int j=0;j<maxColWidth;j++) {
	      text.append(" ");
	    }
	  } else {
	    String ss = t.attribute(i).value((int)attributes[i]);
	    StringBuffer sb = new StringBuffer(ss);

	    for (int j=0;j < (maxColWidth-ss.length()+1); j++) {
		sb.append(" ");
	    }
	    text.append(sb);
	  }
	}
      }
      return text.toString();
    }

    /**
     * Constructor for a hashKey
     *
     * @param t an array of feature values
     */
    public hashKey(double [] t) {

      int i;
      int l = t.length;

      key = -999;
      attributes = new double [l];
      missing = new boolean [l];
      for (i=0;i<l;i++) {
	if (t[i] == Double.MAX_VALUE) {
	  missing[i] = true;
	} else {
	  missing[i] = false;
	  attributes[i] = t[i];
	}
      }
    }

    /**
     * Calculates a hash code
     *
     * @return the hash code as an integer
     */
    public int hashCode() {

      int hv = 0;

      if (key != -999)
	return key;
      for (int i=0;i<attributes.length;i++) {
	if (missing[i]) {
	  hv += (i*13);
	} else {
	  hv += (i * 5 * (attributes[i]+1));
	}
      }
      if (key == -999) {
	key = hv;
      }
      return hv;
    }

    /**
     * Tests if two instances are equal
     *
     * @param b a key to compare with
     */
    public boolean equals(Object b) {

      if ((b == null) || !(b.getClass().equals(this.getClass()))) {
        return false;
      }
      boolean ok = true;
      boolean l;
      if (b instanceof hashKey) {
	hashKey n = (hashKey)b;
	for (int i=0;i<attributes.length;i++) {
	  l = n.missing[i];
	  if (missing[i] || l) {
	    if ((missing[i] && !l) || (!missing[i] && l)) {
	      ok = false;
	      break;
	    }
	  } else {
	    if (attributes[i] != n.attributes[i]) {
	      ok = false;
	      break;
	    }
	  }
	}
      } else {
	return false;
      }
      return ok;
    }

    /**
     * Prints the hash code
     */
    public void print_hash_code() {

      System.out.println("Hash val: "+hashCode());
    }
  }

}

