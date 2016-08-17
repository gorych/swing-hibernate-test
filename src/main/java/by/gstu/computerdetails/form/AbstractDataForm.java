package by.gstu.computerdetails.form;

import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.dao.ScreenResolutionDao;
import by.gstu.computerdetails.dao.impl.MonitorDaoImpl;
import by.gstu.computerdetails.dao.impl.ScreenResolutionDaoImpl;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.Enumeration;

public class AbstractDataForm {

    protected static final MonitorDao MONITOR_DAO;
    protected static final ScreenResolutionDao SCREEN_RESOLUTION_DAO;

    static {
        MONITOR_DAO = new MonitorDaoImpl();
        SCREEN_RESOLUTION_DAO = new ScreenResolutionDaoImpl();
    }

}
