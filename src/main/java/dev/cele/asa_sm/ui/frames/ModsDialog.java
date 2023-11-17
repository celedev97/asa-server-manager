package dev.cele.asa_sm.ui.frames;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.dto.curseforge.ModDto;
import dev.cele.asa_sm.services.ModCacheService;
import dev.cele.asa_sm.ui.components.ScrollablePanel;
import dev.cele.asa_sm.ui.components.server_tab_accordions.AdministrationAccordion;
import dev.cele.asa_sm.ui.components.server_tab_accordions.TopPanel;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Map;
import java.util.Vector;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModsDialog extends JDialog {

    private boolean loading = true;
    private final ModCacheService modCacheService = SpringApplicationContext.autoWire(ModCacheService.class);
    private final Vector<ModDto> mods = new Vector<>();

    private final ScrollablePanel tablePanel;

    public ModsDialog(JFrame mainFrame, AsaServerConfigDto configDto, AdministrationAccordion accordion) {
        super(mainFrame, "Mods", true);
        setSize(500, 500);
        setLocationRelativeTo(mainFrame);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                var newModIds = mods.stream().map(it -> it.getId().toString()).collect(Collectors.joining(","));
                if(!newModIds.equals(configDto.getModIds())){
                    int result = JOptionPane.showConfirmDialog(ModsDialog.this, "Do you want to save the new mod order?", "Save?", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        configDto.setModIds(newModIds);
                        accordion.modIDsField.setText(newModIds);
                    }
                }
                dispose();
            }
        });

        // create a JScrollPane
        tablePanel = new ScrollablePanel(new GridBagLayout());
        tablePanel.setScrollableWidth( ScrollablePanel.ScrollableSizeHint.FIT );
        tablePanel.setScrollableBlockIncrement(
                ScrollablePanel.VERTICAL, ScrollablePanel.IncrementType.PIXELS, 20);

        JScrollPane scrollPane = new JScrollPane( tablePanel );
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        new Thread(() -> {
            mods.addAll(modCacheService.getMods(configDto.getModIds()));
            loading = false;
            updateMods();
        }).start();

        setSize(880, 600);
        setMinimumSize(new Dimension(720, 600));
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    void updateMods() {
        tablePanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        tablePanel.add(new JLabel("ID"){{
            putClientProperty(FlatClientProperties.STYLE_CLASS, "large");
        }}, gbc);

        gbc.gridx++;
        tablePanel.add(new JLabel("Name"){{
            putClientProperty(FlatClientProperties.STYLE_CLASS, "large");
        }}, gbc);

        gbc.gridx++;
        tablePanel.add(new JLabel("Description"){{
            putClientProperty(FlatClientProperties.STYLE_CLASS, "large");
        }}, gbc);

        gbc.gridx++;
        tablePanel.add(new JLabel("Actions"){{
            putClientProperty(FlatClientProperties.STYLE_CLASS, "large");
        }}, gbc);

        for (int i = 0; i < mods.size(); i++) {
            var mod = mods.get(i);
            gbc.gridy++;
            gbc.gridx = 0;

            tablePanel.add(new JLabel(mod.getId().toString()), gbc);

            gbc.gridx++;
            tablePanel.add(new JLabel(mod.getName()), gbc);

            gbc.gridx++;
            gbc.weightx = 1;
            gbc.weighty = 1;
            tablePanel.add(new JLabel("<html>"+mod.getSummary()+"</html>"), gbc);
            gbc.weightx = 0;
            gbc.weighty = 0;

            gbc.gridx++;
            JPanel actionsPanel = new JPanel();
            setLayout(new GridLayout(1, 3));

            //up button to move mod up
            JButton upButton = new JButton("˄");
            upButton.addActionListener(e -> {
                int index = mods.indexOf(mod);
                if (index > 0) {
                    mods.remove(index);
                    mods.add(index - 1, mod);
                    updateMods();
                }
            });
            if(i == 0) upButton.setEnabled(false);

            //down button to move mod down
            JButton downButton = new JButton("˅");
            downButton.addActionListener(e -> {
                int index = mods.indexOf(mod);
                if (index < mods.size() - 1) {
                    mods.remove(index);
                    mods.add(index + 1, mod);
                    updateMods();
                }
            });
            if(i == mods.size() - 1) downButton.setEnabled(false);

            //button with unicode red x character
            JButton removeButton = new JButton("✖");
            removeButton.setForeground(Color.RED);
            removeButton.addActionListener(e -> {
                mods.remove(mod);
                updateMods();
            });

            actionsPanel.add(upButton);
            actionsPanel.add(downButton);
            actionsPanel.add(removeButton);
            tablePanel.add(actionsPanel, gbc);
        }

        repaint();
        revalidate();
    }

}

