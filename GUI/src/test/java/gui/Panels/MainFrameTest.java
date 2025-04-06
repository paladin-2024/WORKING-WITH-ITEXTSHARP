package gui.Panels;

import static org.junit.jupiter.api.Assertions.*;

class MainFrameTest {
    // Test the MainFrame class
    @org.junit.jupiter.api.Test
    void testMainFrame() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.show();

        // Check if the frame is visible
        assertTrue(mainFrame.frame.isShowing());

        // Check if the tabbed pane has the correct number of tabs
        assertEquals(3, mainFrame.tabbedPane.getTabCount());

        // Check if the tabs have the correct titles
        assertEquals("Employees", mainFrame.tabbedPane.getTitleAt(0));
        assertEquals("Departments", mainFrame.tabbedPane.getTitleAt(1));
        assertEquals("Reports", mainFrame.tabbedPane.getTitleAt(2));
    }

}