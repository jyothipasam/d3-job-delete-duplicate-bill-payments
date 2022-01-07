# Purpose

[![Build Status](https://github.com/LodoSoftware/d3-job-cleanup-transaction/actions/workflows/build.yml/badge.svg)](https://github.com/LodoSoftware/d3-job-cleanup-transaction/actions)

This µs houses the Transaction Cleanup job. It will delete old / soft-deleted transactions, on a schedule.

# Architecture
This follows the D3-N3xt v4 job architecture.
See `d3-job-trigger`'s wiki for details.
In short, there is a separate scheduler/"ticker" which will fire off
an event for this job according to a schedule.
This µs listens for those events, and performs transaction cleanup upon receiving one.
