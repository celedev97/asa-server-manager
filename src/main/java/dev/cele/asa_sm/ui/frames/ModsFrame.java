package dev.cele.asa_sm.ui.frames;

import dev.cele.asa_sm.config.SpringApplicationContext;
import dev.cele.asa_sm.dto.AsaServerConfigDto;
import dev.cele.asa_sm.dto.curseforge.ModDto;
import dev.cele.asa_sm.services.ModCacheService;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class ModsFrame extends JDialog {

    private boolean loading = true;
    private final ModCacheService modCacheService = SpringApplicationContext.autoWire(ModCacheService.class);
    private final Vector<ModDto> mods = new Vector<>();

    public ModsFrame(JFrame mainFrame, AsaServerConfigDto configDto) {
        super(mainFrame, "Mods", true);
        setSize(500, 500);
        setLocationRelativeTo(mainFrame);

        // create a JScrollPane
        JPanel viewPort = new JPanel();
        viewPort.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(viewPort);
        add(scrollPane);

        // create a table model

        new Thread(() -> {
            var modDtos = modCacheService.getMods(configDto.getModIds());
            mods.removeAllElements();
            mods.addAll(modDtos);
            loading = false;
        }).start();

        setSize(400, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
