package com.linkedin.d2.balancer.event;

import java.util.List;


/**
 * {@link D2Monitor} records D2 events for reporting and analyzing purpose
 *
 * Each D2Monitor is associated with one service, including cluster stats
 * and list of URIs (hosts) in the cluster.
 *
 */
public class D2Monitor
{
  private final String _serviceName;
  private final String _clusterName;
  private final ClusterStats _clusterStats;
  private final List<UriInfo> _uriList;
  private final int _partitionId;
  private final long _intervalMs;  // The intervals in millisecond since last emission

  /**
   * To control the data volume generated by D2Monitor, sampling mechanisms are required. So far the following
   * 2 approaches are used:
   *
   * 1. Control the sampling rate. For health service, the client emits the monitor event at a slow pace.
   *    Depends on the total client number, this can be one minute to multi minutes. When the client sees
   *    bad hosts, it changes to a faster pace to emit.
   * 2. To further reduce the volume, uriList will mostly include the specific hosts (eg hosts health issue).
   *
   */

  D2Monitor(String serviceName, String clusterName, ClusterStats clusterStats, List<UriInfo> uriList, int partitionId, long intervalMs)
  {
    _serviceName = serviceName;
    _clusterName = clusterName;
    _clusterStats = clusterStats;
    _uriList = uriList;
    _partitionId = partitionId;
    _intervalMs = intervalMs;
  }

  public String getServiceName()
  {
    return _serviceName;
  }

  public String getClusterName()
  {
    return _clusterName;
  }

  public List<UriInfo> getUriList()
  {
    return _uriList;
  }

  public ClusterStats getClusterStats()
  {
    return _clusterStats;
  }

  public int getPartitionId()
  {
    return _partitionId;
  }

  public long getIntervalMs()
  {
    return _intervalMs;
  }

  @Override
  public String toString()
  {
    return "D2Monitor (service=" + _serviceName + "),"
        + "(cluster=" + _clusterName + "),"
        + "(clusterStats=" + _clusterStats + "),"
        + "[Uris: " + _uriList + "]";
  }

  /**
   * {@link ClusterStats} reports the cluster stats
   */
  public static class ClusterStats
  {
    private final long _clusterCallCount;
    private final double _clusterAverageLatency;
    private final long _clusterDroppedCalls;
    private final long _clusterErrorCount;
    private final long _clusterFailedRouteCalls;
    private final double _clusterDropLevel;
    private final int _clusterNumHosts;

    ClusterStats(long callCount, double averageLatency, long droppedCalls,
        long clusterErrorCount, long failedToRoute, double dropLeve, int clusterNumHosts)
    {
      _clusterCallCount = callCount;
      _clusterAverageLatency = averageLatency;
      _clusterDroppedCalls = droppedCalls;
      _clusterErrorCount = clusterErrorCount;
      _clusterFailedRouteCalls = failedToRoute;
      _clusterDropLevel = dropLeve;
      _clusterNumHosts = clusterNumHosts;
    }

    public long getClusterCallCount()
    {
      return _clusterCallCount;
    }

    public double getClusterAverageLatency()
    {
      return _clusterAverageLatency;
    }

    public long getClusterDroppedCalls()
    {
      return _clusterDroppedCalls;
    }

    public long getClusterErrorCount()
    {
      return _clusterErrorCount;
    }

    public long getClusterFailedRouteCalls()
    {
      return _clusterFailedRouteCalls;
    }

    public double getClusterDropLevel()
    {
      return _clusterDropLevel;
    }

    public int getClusterNumHosts()
    {
      return _clusterNumHosts;
    }
  }

  /**
   * {@link UriInfo} reports the URI stats and events
   */
  public static class UriInfo
  {
    private final String _hostName;
    private final int _portNumber;
    private final long _currentCallCount;
    private final long _totalCallCount;
    private final long _outstandingCount;
    private final double _currentLatency;
    private final int _currentErrorCount;
    private final long _50PctLatency;
    private final long _90PctLatency;
    private final long _95PctLatency;
    private final long _99PctLatency;
    private final long _quarantineDuration;
    private final double _computedDropRate;
    private final int _transmissionPoints;

    UriInfo(String hostName, int portNumber, long callCount,
        long totalCallCount, long outstandingCount, double currentLatency, int errorCount,
        long a50PctLatency, long a90PctLatency, long a95PctLatency,
        long a99PctLatency, long quarantineDuration, double computedDropRate, int transmissionPoints)
    {
      _hostName = hostName;
      _portNumber = portNumber;
      _currentCallCount = callCount;
      _totalCallCount = totalCallCount;
      _outstandingCount = outstandingCount;
      _currentLatency = currentLatency;
      _currentErrorCount = errorCount;
      _50PctLatency = a50PctLatency;
      _90PctLatency = a90PctLatency;
      _95PctLatency = a95PctLatency;
      _99PctLatency = a99PctLatency;
      _quarantineDuration = quarantineDuration;
      _computedDropRate = computedDropRate;
      _transmissionPoints = transmissionPoints;
    }

    public String getHostName()
    {
      return _hostName;
    }

    public int getPortNumber()
    {
      return _portNumber;
    }

    public long getCurrentCallCount()
    {
      return _currentCallCount;
    }

    public double getCurrentLatency()
    {
      return _currentLatency;
    }

    public int getCurrentErrorCount()
    {
      return _currentErrorCount;
    }

    public long getTotalCallCount()
    {
      return _totalCallCount;
    }

    public long getOutstandingCount()
    {
      return _outstandingCount;
    }

    public long get50PctLatency()
    {
      return _50PctLatency;
    }

    public long get90PctLatency()
    {
      return _90PctLatency;
    }

    public long get95PctLatency()
    {
      return _95PctLatency;
    }

    public long get99PctLatency()
    {
      return _99PctLatency;
    }

    public long getQuarantineDuration()
    {
      return _quarantineDuration;
    }

    public double getComputedDropRate()
    {
      return _computedDropRate;
    }

    public int getTransmissionPoints()
    {
      return _transmissionPoints;
    }
  }
}
