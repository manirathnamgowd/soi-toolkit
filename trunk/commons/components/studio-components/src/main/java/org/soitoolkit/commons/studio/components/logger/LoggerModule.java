/**
 * This file was automatically generated by the Mule Development Kit
 */
package org.soitoolkit.commons.studio.components.logger;

import java.util.Map;

import javax.inject.Inject;

import org.mule.RequestContext;
import org.mule.api.MuleEvent;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Module;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.param.Optional;
//import org.soitoolkit.commons.logentry.schema.v1.LogLevelType;
//import org.soitoolkit.commons.module.logger.api.EventLogger;
//import org.soitoolkit.commons.module.logger.impl.DefaultEventLogger;
import org.springframework.stereotype.Component;

/**
 * soi-toolkit module for logging
 * 
 * @author Magnus Larsson
 */
@SuppressWarnings("deprecation")
@Component
@Module(name="st-logger", schemaVersion="0.5.1-SNAPSHOT")
public class LoggerModule {

	/*
	 * TODO:
	 * - in i svn
	 * - få igång mot st utan extern deps
	 * - kolla av parametrar som används i vgr, t ex e-handel...
	 * - hur få in soitoolkit som namn i pluginen så att det inte bara står "Mule Cloud Connector Mule Studio Extension" i mule studio 
	 * - ersätt standard apache licens med vår egen!
	 * - updat mvn-enforcer till 1.1.1 * 2 pga http://jira.codehaus.org/browse/MENFORCER-117 och module-logger 
	 * - ta bort beroende till slf4j och se till att den (3 jar-filer) inte paketeras med i zip-fil eller update-site
	 * - ta bort beroende till commons-mule, kopiera in kod helt enkelt...
	 * - ta bort pa
	 * + separata metoder för varje loggnivå
	 * + bort med mule-module-rest 1.0 och dep till devkit 3.0.2 
	 * - DI fallback i getters måste vara att default impl instansieras...
	 * - classpath scanning måste med i alla projekt...
	 * - dekl av defaulteventlogger måste oxå med???
	 * - impl detaljer...
	 *   - få bort håtdkodningar i form av könamn och connectorer, flytta ut till config-element med bra defaultväden, typ properties i property fil...
	 * + busContext, behövs det eller räcker det med extraInfo + correlationId
	 * - behövs busCorrId som namn eller kan vi bara kalla det corrId och defaulta det till mule's?
	 * - bättre namn i mule studio
	 * - ikoner...
	 * - explicita set*Beans på config-element?
	 *
	 */

	/**
	 * Optional name of the integration scenario or business process
     */
	@Optional @Configurable
    private String integrationScenario;

	/**
     * Set property
	 * 
	 * @param integrationScenario Integration scenario
	 */
	public void setIntegrationScenario(String integrationScenario)
    {
		System.err.println("### GOT STATIC IS: " + integrationScenario);
        this.integrationScenario = integrationScenario;
    }

	/**
     * Get property
     */
    public String getIntegrationScenario()
    {
        return integrationScenario;
    }


	/**
	 *  Optional extra info
     */
	@Optional @Configurable
    private Map<String, String> extraInfo;

	/**
     * Set property
	 * 
	 * @param extraInfo Extra info
	 */
	public void setExtraInfo(Map<String, String> extraInfo)
    {
		System.err.println("### GOT STATIC EI: " + extraInfo);
        this.extraInfo = extraInfo;
    }
	
	/**
     * Get property
     */
    public Map<String, String> getExtraInfo()
    {
        return extraInfo;
    }
	

	/*
	* @param extra Optional extra info
    * @return The incoming payload
    * / 
   @Processor
   public Object logTrace(
   	@Optional @FriendlyName("Log Message") String message, 
   	@Optional String integrationScenario, 
   	@Optional String messagType,
   	@Optional String contractId,
   	@Optional String correlationId,
   	@Optional @FriendlyName("Extra Info") Map<String, String> extraInfo) {
	*/
	
    /*
     * Dependencies
     * /
    private EventLogger eventLogger;

/*
    @Inject
    public void setEventLogger(EventLogger eventLogger) {
    	this.eventLogger = eventLogger;
    }
* /    
	protected EventLogger getEventLogger() {
    	if (eventLogger == null) {
    		// Fallback if classpath-scanning is missing, eg: <context:component-scan base-package="org.soitoolkit.commons.module.logger" />
    		eventLogger = new DefaultEventLogger();
    	}
    	return eventLogger;
    }
*/

    /**
     * Log processor for level TRACE
     *
     * {@sample.xml ../../../doc/soitoolkit-connector.xml.sample soitoolkit:log-trace}
     *
     * @param message Log-message to be processed
	 * @param integrationScenario Optional name of the integration scenario or business process
	 * @param messageType Optional name of the message type, e.g. a XML Schema namespace for a XML payload
	 * @param contractId Optional name of the contract in use
	 * @param correlationId Optional correlation identity of the message
     * @param extraInfo Optional extra info
     * @return The incoming payload
     */ 
    @Processor
    public Object logTrace(
    	@Optional @FriendlyName("Log Message") String message, 
    	@Optional String integrationScenario, 
    	@Optional String messageType,
    	@Optional String contractId,
    	@Optional String correlationId,
    	@Optional @FriendlyName("Extra Info") Map<String, String> extraInfo) {

    	System.err.println("### MY COMP LOGS!");
    	System.err.println("message: " + message);
    	System.err.println("integrationScenario: " + integrationScenario);
    	System.err.println("messageType: " + messageType);
    	System.err.println("contractId: " + contractId);
    	System.err.println("correlationId: " + correlationId);
    	System.err.println("integrationScenario: " + integrationScenario);
    	System.err.println("extraInfo: " + extraInfo);
    	System.err.println("static integrationScenario: " + this.integrationScenario);
    	System.err.println("static extraInfo: " + this.extraInfo);

    	MuleEvent muleEvent = RequestContext.getEvent();
    	return muleEvent.getMessage().getPayload();

    	// return doLog(LogLevelType.TRACE, message, integrationScenario, contractId, correlationId, extra);
    }

    /**
     * Log processor for level DEBUG
     *
     * {@sample.xml ../../../doc/SoitoolkitLogger-connector.xml.sample soitoolkitlogger:log}
     *
     * @param message Log-message to be processed
	 * @param integrationScenario Optional name of the integration scenario or business process
	 * @param contractId Optional name of the contract in use
	 * @param correlationId Optional correlation identity of the message
     * @param extra Optional extra info
     * @return The incoming payload
     * / 
    @Processor
    public Object logDebug(
    	String message, 
    	@Optional String integrationScenario, 
    	@Optional String contractId, 
    	@Optional String correlationId,
    	@Optional Map<String, String> extra) {

    	return doLog(LogLevelType.DEBUG, message, integrationScenario, contractId, correlationId, extra);
    }

    /**
     * Log processor for level INFO
     *
     * {@sample.xml ../../../doc/SoitoolkitLogger-connector.xml.sample soitoolkitlogger:log}
     *
     * @param message Log-message to be processed
	 * @param integrationScenario Optional name of the integration scenario or business process
	 * @param contractId Optional name of the contract in use
	 * @param correlationId Optional correlation identity of the message
     * @param extra Optional extra info
     * @return The incoming payload
     * / 
    @Processor
    public Object logInfo(
    	String message, 
    	@Optional String integrationScenario, 
    	@Optional String contractId, 
    	@Optional String correlationId,
    	@Optional Map<String, String> extra) {

    	return doLog(LogLevelType.INFO, message, integrationScenario, contractId, correlationId, extra);
    }

    /**
     * Log processor for level WARNING
     *
     * {@sample.xml ../../../doc/SoitoolkitLogger-connector.xml.sample soitoolkitlogger:log}
     *
     * @param message Log-message to be processed
	 * @param integrationScenario Optional name of the integration scenario or business process
	 * @param contractId Optional name of the contract in use
	 * @param correlationId Optional correlation identity of the message
     * @param extra Optional extra info
     * @return The incoming payload
     * / 
    @Processor
    public Object logWarning(
    	String message, 
    	@Optional String integrationScenario, 
    	@Optional String contractId, 
    	@Optional String correlationId,
    	@Optional Map<String, String> extra) {

    	return doLog(LogLevelType.WARNING, message, integrationScenario, contractId, correlationId, extra);
    }

    / **
     * Log processor for level ERROR
     *
     * {@sample.xml ../../../doc/SoitoolkitLogger-connector.xml.sample soitoolkitlogger:log}
     *
     * @param message Log-message to be processed
	 * @param integrationScenario Optional name of the integration scenario or business process
	 * @param contractId Optional name of the contract in use
	 * @param correlationId Optional correlation identity of the message
     * @param extra Optional extra info
     * @return The incoming payload
     * / 
    @Processor
    public Object logError(
    	String message, 
    	@Optional String integrationScenario, 
    	@Optional String contractId, 
    	@Optional String correlationId,
    	@Optional Map<String, String> extra) {

    	return doLog(LogLevelType.ERROR, message, integrationScenario, contractId, correlationId, extra);
    }

    protected Object doLog(
    	LogLevelType level,
    	String message, 
    	String integrationScenario, 
    	String contractId, 
    	String correlationId,
    	Map<String, String> extra) {

    	// Get the MuleEvent from the RequestContent instead of having payload and headers injected in method call.
    	// Injecting the payload will cause an evaluation of the expression [#payload] on every call, so it will be a performance killer...
    	// MuleEvent also includes a lot of information that we can't get injected
		MuleEvent muleEvent = RequestContext.getEvent();

    	getEventLogger().logEvent(muleEvent, message, level, integrationScenario, contractId, correlationId, extra);

    	return muleEvent.getMessage().getPayload();
    }
*/    
}