module GUI {
    exports gui;
    exports util;
    exports gui.Panels;
    exports gui.dialog;
    exports gui.TableModel;
    requires Utils;
    requires itextpdf;
    requires kernel;
    requires layout;
    requires jdk.hotspot.agent;
    requires java.desktop;
    requires Core;
    requires java.sql;
}