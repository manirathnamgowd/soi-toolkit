/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.soitoolkit.commons.module.logger;

import java.util.List;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.construct.Flow;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.FunctionalTestCase;
import org.mule.module.client.MuleClient;
import org.soitoolkit.commons.logentry.schema.v1.LogEvent;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.soitoolkit.commons.mule.test.AbstractJmsTestUtil;
import org.soitoolkit.commons.mule.test.ActiveMqJmsTestUtil;

@SuppressWarnings("deprecation")
public class SoitoolkitLoggerModuleTest extends FunctionalTestCase
{
	private static final String INFO_LOG_QUEUE = "SOITOOLKIT.LOG.INFO";
	private static final String ERROR_LOG_QUEUE = "SOITOOLKIT.LOG.ERROR";
	private static final JaxbUtil JAXB_UTIL = new JaxbUtil(LogEvent.class);
	private AbstractJmsTestUtil jmsUtil = null;

	@Override
    protected String getConfigResources()
    {
        return "mule-config.xml";
    }

    @Override
	protected void doSetUp() throws Exception {
		super.doSetUp();

		doSetUpJms();
    }

	private void doSetUpJms() {
		// TODO: Fix lazy init of JMS connection et al so that we can create jmsutil in the declaration
		// (The embedded ActiveMQ queue manager is not yet started by Mule when jmsutil is delcared...)
		if (jmsUtil == null) jmsUtil = new ActiveMqJmsTestUtil();

		// Clear queues used for error handling
		jmsUtil.clearQueues(INFO_LOG_QUEUE, ERROR_LOG_QUEUE);
    }

	@Test
    public void testFlow() throws Exception {

        assertEquals("Unexpected messages before test", 0, jmsUtil.browseMessagesOnQueue(INFO_LOG_QUEUE).size());
        assertEquals("Unexpected messages before test", 0, jmsUtil.browseMessagesOnQueue(ERROR_LOG_QUEUE).size());
		
		String testPayload = "My test-payload";
    	MuleClient c = new MuleClient(muleContext);
    	MuleMessage r = c.send("vm:/in", testPayload, null);
    	assertEquals(testPayload, r.getPayload());

        List<Message> msgs = jmsUtil.browseMessagesOnQueue(INFO_LOG_QUEUE);
        assertEquals("Incorrect number of info-messages", 1, msgs.size());
        LogEvent le = (LogEvent)JAXB_UTIL.unmarshal(((TextMessage)msgs.get(0)).getText());
        assertEquals("INFO", le.getLogEntry().getMessageInfo().getLevel().toString());
        assertEquals(testPayload, le.getLogEntry().getPayload());
        assertEquals(3, le.getLogEntry().getExtraInfo().size());

        msgs = jmsUtil.browseMessagesOnQueue(ERROR_LOG_QUEUE);
        assertEquals("Incorrect number of error-messages", 1, msgs.size());
        le = (LogEvent)JAXB_UTIL.unmarshal(((TextMessage)msgs.get(0)).getText());
        assertEquals("WARNING", le.getLogEntry().getMessageInfo().getLevel().toString());
        assertEquals(testPayload, le.getLogEntry().getPayload());
        assertEquals(0, le.getLogEntry().getExtraInfo().size());
    }

    @Test
    public void testFlow_GeneratedTest() throws Exception
    {
        runFlowAndExpect("testFlow", "My test-payload");
    }

    /**
    * Run the flow specified by name and assert equality on the expected output
    *
    * @param flowName The name of the flow to run
    * @param expect The expected output
    */
    protected <T> void runFlowAndExpect(String flowName, T expect) throws Exception
    {
        Flow flow = lookupFlowConstruct(flowName);
        MuleEvent event = AbstractMuleTestCase.getTestEvent(expect);
        MuleEvent responseEvent = flow.process(event);

        assertEquals(expect, responseEvent.getMessage().getPayload());
    }

    /**
    * Run the flow specified by name using the specified payload and assert
    * equality on the expected output
    *
    * @param flowName The name of the flow to run
    * @param expect The expected output
    * @param payload The payload of the input event
    */
    protected <T, U> void runFlowWithPayloadAndExpect(String flowName, T expect, U payload) throws Exception
    {
        Flow flow = lookupFlowConstruct(flowName);
        MuleEvent event = AbstractMuleTestCase.getTestEvent(payload);
        MuleEvent responseEvent = flow.process(event);

        assertEquals(expect, responseEvent.getMessage().getPayload());
    }

    /**
     * Retrieve a flow by name from the registry
     *
     * @param name Name of the flow to retrieve
     */
    protected Flow lookupFlowConstruct(String name)
    {
        return (Flow) muleContext.getRegistry().lookupFlowConstruct(name);
    }
}
