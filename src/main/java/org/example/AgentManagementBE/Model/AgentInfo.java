package org.example.AgentManagementBE.Model;

public class AgentInfo 
{
    private int agentID;
    private String agentName;

    public AgentInfo(int agentID, String agentName) 
    {
        this.agentID = agentID;
        this.agentName = agentName;
    }

    public String getAgentName() 
    {
        return agentName;
    }

    public int getAgentID() 
    {
        return agentID;
    }
}