package no.codify.x2p;

import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;

public class Xml2PropertiesTest {

    @Test
    public void shouldConvertXmlFileToProperties() throws Exception {
        Either<Exception, Map<?,?>> result = Xml2Properties.from( new File("src/test/resources/meta-desc.xml" ));

        if( result.isLeft() ) {
            throw result.left();
        }

        System.out.println( result.right() );
        assertEquals( "foo-bar-app", result.right().get( "meta-desc.app.name" ) );
    }

}