/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contacts.logic;

import java.util.Properties;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Dr. Volker Riediger <riediger@uni-koblenz.de>
 */
public class ContactsLogicTest {

    private static EJBContainer container;
    private static Context ctx;

    private ContactsLogic cl;

    @BeforeClass
    public static void setup() {
        Properties props = new Properties();
        // props.setProperty(EJBContainer.MODULES, "JavaEE2019-ejb");
        System.err.println("START CONTAINER");
        container = EJBContainer.createEJBContainer(props);
        ctx = container.getContext();
    }

    @Before
    public void init() throws NamingException {
        System.err.println("CREATE LOGIC BEAN");
        cl = (ContactsLogic) ctx.lookup("java:global/Contacts/Contacts-ejb/ContactsLogicImpl!contacts.logic.ContactsLogic");
    }

    @AfterClass
    public static void teardown() {
        if (container != null) {
            System.err.println("CLOSE CONTAINER");
            container.close();
        }
        System.err.println("FINI!");
    }

    /**
     * Test of createContact method, of class ContactsLogic.
     */
    @Test
    public void testCreateContact() {
        // TODO
    }

    /**
     * Test of getContactList method, of class ContactsLogic.
     */
    @Test
    public void testGetContactList() {
        // TODO
    }

    /**
     * Test of search method, of class ContactsLogic.
     */
    @Test
    public void testSearch() {
        // TODO
    }
}
