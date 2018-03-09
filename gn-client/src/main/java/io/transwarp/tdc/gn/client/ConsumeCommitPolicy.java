package io.transwarp.tdc.gn.client;

public enum ConsumeCommitPolicy {
    /**
     * Auto commit means the underlying client will do auto commit based on
     * the auto commit interval
     */
    Auto,
    /**
     * Single commit is a strict policy, commit once each record processed
     */
    Single,
    /**
     * Commit once a collection of records(from one poll() call) processed
     */
    Batch,
}
