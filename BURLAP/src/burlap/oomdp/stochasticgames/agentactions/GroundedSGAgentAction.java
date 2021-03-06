package burlap.oomdp.stochasticgames.agentactions;

import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.states.State;


/**
 * A {@link burlap.oomdp.stochasticgames.agentactions.GroundedSGAgentAction} is a high-level implementation
 * of the {@link burlap.oomdp.core.AbstractGroundedAction} that is closely associated with the {@link burlap.oomdp.stochasticgames.agentactions.SGAgentAction}
 * definition of actions that can be taken by agents in a multi-agent stochastic game. The role of a {@link burlap.oomdp.stochasticgames.agentactions.GroundedSGAgentAction}
 * is to provide a reference to a corresponding {@link burlap.oomdp.stochasticgames.agentactions.SGAgentAction}, a reference
 * to the name of the agent who is taking the action, and any other parameter assignment information that is necessary
 * for the agent to apply the action. The set of possible {@link burlap.oomdp.stochasticgames.agentactions.GroundedSGAgentAction} instances
 * specifying the set of possible parameter assignments an action can apply will be generated by the associated
 * {@link burlap.oomdp.stochasticgames.agentactions.SGAgentAction} method
 * {@link burlap.oomdp.stochasticgames.agentactions.SGAgentAction#getAllApplicableGroundedActions(burlap.oomdp.core.states.State, String)}.
 * See the {@link burlap.oomdp.stochasticgames.agentactions.SGAgentAction} class documentation for more information on
 * defining parameterized {@link burlap.oomdp.stochasticgames.agentactions.SGAgentAction}s.
 * <br/><br/>
 * This high-level abstract class requires implementing a few methods:<br/>
 * {@link #copy()},<br/>
 * {@link #initParamsWithStringRep(String[])} and,<br/>
 * {@link #getParametersAsString()}.<br/>
 * The {@link #copy()} method should be override to return a new instance of your subclass with all parameter assignment information
 * copied over. The latter two methods are necessary for supporting all of the BURLAP's tools with parameterized actions, but
 * may not be needed for critical operations.
 * The {@link #initParamsWithStringRep(String[])} allows the parameters of an action to be initialized from a string representation.
 * The {@link #getParametersAsString()} method gets a string representation of the parameters. If your action is
 * not parameterized, then you can simply use the {@link burlap.oomdp.stochasticgames.agentactions.SimpleGroundedSGAgentAction}
 * implementation.
 *
 * <br/><br/>
 * If you plan on
 * making an {@link burlap.oomdp.stochasticgames.agentactions.SGAgentAction} definition with OO-MDP object parameters, you can use the existing
 * {@link burlap.oomdp.stochasticgames.agentactions.ObParamSGAgentAction}, which is associated with the {@link burlap.oomdp.singleagent.GroundedAction}
 * subclass {@link burlap.oomdp.stochasticgames.agentactions.ObParamSGAgentAction.GroundedObParamSGAgentAction}.
 *
 * @author James MacGlashan
 *
 */
public abstract class GroundedSGAgentAction implements AbstractGroundedAction {

	/**
	 * The name of the agent performing the action
	 */
	public String actingAgent;
	
	/**
	 * The single action the acting agent will be performing
	 */
	public SGAgentAction action;



	/**
	 * Initializes this object with the name of the acting agent, the SingleAction reference, and the parameters used.
	 * @param actingAgent the acting agent.
	 * @param a the {@link SGAgentAction}.
	 */
	public GroundedSGAgentAction(String actingAgent, SGAgentAction a){
		this.actingAgent = actingAgent;
		this.action = a;
	}


	/**
	 * Returns whether this action is applicable (satisfies the preconditions) in the given input {@link burlap.oomdp.core.states.State}
	 * @param s the input state to check against
	 * @return true if this action is applicable (satisfies the preconditions) in the given input {@link burlap.oomdp.core.states.State}; false otherwise
	 */
	public boolean applicableInState(State s){
		return this.action.applicableInState(s, this);
	}


	/**
	 * Returns a string specifying the action name and parameters used in this GroundedSingleAction.
	 * @return a string specifying the action name and parameters used in this GroundedSingleAction.
	 */
	public String justActionString(){
		StringBuilder buf = new StringBuilder();
		buf.append(actionName());
		String [] strParams = this.getParametersAsString();
		if(strParams != null) {
			for(String param : strParams) {
				buf.append(" ").append(param);
			}
		}

		return buf.toString();
		
	}
	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder();
		buf.append(actingAgent).append(":");
		buf.append(action.actionName);
		String [] strParams = this.getParametersAsString();
		if(strParams != null) {
			for(String param : strParams) {
				buf.append(" ").append(param);
			}
		}

		return buf.toString();
	}
	
	
	@Override
	public boolean equals(Object other){
		
		if(this == other){
			return true;
		}
		
		if(!(other instanceof GroundedSGAgentAction)){
			return false;
		}
		
		GroundedSGAgentAction go = (GroundedSGAgentAction)other;
		
		if(!this.actingAgent.equals(go.actingAgent)){
			return false;
		}
		
		if(!this.action.actionName.equals(go.action.actionName)){
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode(){
		String shortName = this.actingAgent + "::" + this.actionName();
		return shortName.hashCode();
	}


	@Override
	public String actionName() {
		return this.action.actionName;
	}


	@Override
	public abstract GroundedSGAgentAction copy();


	@Override
	public boolean isParameterized() {
		return this.action.isParameterized();
	}


}
