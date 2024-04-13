package cz.cvut.omo.sp.manager;

import cz.cvut.omo.sp.reports.ReportVisitor;

public interface HomeVisitor {
    void acceptVisitor(ReportVisitor visitor);
}
