package cz.cvut.omo.sp.reports;

import cz.cvut.omo.sp.manager.SmartHomeManager;

public interface ReportVisitor {

    void visit(SmartHomeManager smartHomeManager);

}
