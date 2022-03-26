package org.apache.hop.pipeline.transforms.formula.function;

import org.apache.hop.core.exception.HopXmlException;
import org.apache.hop.core.xml.XmlHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FunctionLib {
    private List<FunctionDescription> functions;

    public FunctionLib( String filename ) throws HopXmlException {
        functions = new ArrayList<FunctionDescription>();

        Document document = XmlHandler.loadXmlFile( getClass().getResourceAsStream( filename ) );
        Node functionsNode = XmlHandler.getSubNode( document, "libformula-functions" );
        int nrFunctions = XmlHandler.countNodes( functionsNode, FunctionDescription.XML_TAG );
        for ( int i = 0; i < nrFunctions; i++ ) {
            Node functionNode = XmlHandler.getSubNodeByNr( functionsNode, FunctionDescription.XML_TAG, i );
            this.functions.add( new FunctionDescription( functionNode ) );
        }
    }

    /**
     * @return the functions
     */
    public List<FunctionDescription> getFunctions() {
        return functions;
    }

    /**
     * @param functions
     *          the functions to set
     */
    public void setFunctions( List<FunctionDescription> functions ) {
        this.functions = functions;
    }

    /**
     * @return A sorted array of function names, extracted from the function descriptions...
     */
    public String[] getFunctionNames() {
        String[] names = new String[functions.size()];
        for ( int i = 0; i < functions.size(); i++ ) {
            names[i] = functions.get( i ).getName();
        }
        Arrays.sort( names );
        return names;
    }

    /**
     * @return A sorted array of unique categories, extracted from the function descriptions...
     */
    public String[] getFunctionCategories() {
        List<String> categories = new ArrayList<String>();
        for ( FunctionDescription function : functions ) {
            String category = function.getCategory();
            if ( !categories.contains( category ) ) {
                categories.add( category );
            }
        }
        Collections.sort( categories );
        return categories.toArray( new String[categories.size()] );
    }

    /**
     * Get all the function names for a certain category
     *
     * @param category
     *          the category name to look for
     * @return the sorted array of function names for the specified category
     */
    public String[] getFunctionsForACategory( String category ) {
        List<String> names = new ArrayList<String>();
        for ( FunctionDescription function : functions ) {
            if ( function.getCategory().equalsIgnoreCase( category ) ) {
                names.add( function.getName() );
            }
        }
        Collections.sort( names );
        return names.toArray( new String[names.size()] );
    }

    /**
     * @param functionName
     *          the name of the function to look for
     * @return the corresponding function description or null if nothing was found.
     */
    public FunctionDescription getFunctionDescription( String functionName ) {
        for ( FunctionDescription function : functions ) {
            if ( function.getName().equalsIgnoreCase( functionName ) ) {
                return function;
            }
        }
        return null;
    }
}
