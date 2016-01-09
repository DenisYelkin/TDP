/*
 * Copyright (c) 2010, Oracle. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.students.view;

import com.students.controller.Controller;
import com.students.controller.EntityListener;
import com.students.controller.ListOfEntitiesListener;
import com.students.controller.MessageListener;
import com.students.entity.BaseEntity;
import com.students.entity.Actor;
import com.students.entity.Director;
import com.students.entity.EntityType;
import com.students.entity.Movie;
import com.students.entity.Персонаж;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

public class MainForm extends javax.swing.JFrame {

    private Controller controller;
    private final ListOfEntitiesListener dataListener = new ListOfEntitiesListener() {

        @Override
        public void onDataReceive(EntityType type, List<? extends BaseEntity> entities) {
            switch (type) {
                case MOVIE:
                    entitiesTable.setModel(TableModels.getMovieTableModel((List<Movie>) entities));
                    break;
                case CHARACTER:
                    entitiesTable.setModel(TableModels.getCharacterTableModel((List<Персонаж>) entities));
                    break;
                case DIRECTOR:
                    entitiesTable.setModel(TableModels.getDirectorTableModel((List<Director>) entities));
                    break;
                case ACTOR:
                    entitiesTable.setModel(TableModels.getActorTableModel((List<Actor>) entities));
                    break;
                case FAKE:
                    entitiesTable.setModel(new DefaultTableModel());
                    break;
                default:
                    break;
            }
        }
    };

    private final MessageListener messageListener = new MessageListener() {

        @Override
        public void onMessageReceive(EntityType type) {
            JOptionPane.showMessageDialog(null, String.format("Кто-то внес изменения в: %s\nВам следует нажать Refresh", type.getName()));
        }
    };

    private final EntityListener entityListener = new EntityListener() {

        @Override
        public void onEntityReceive(EntityType type, BaseEntity entity) {
            JFrame form = null;
            try {
                switch (type) {
                    case FAKE:
                        JOptionPane.showMessageDialog(null, "Эта сущность в данный момент редактируется другим пользователем");
                        break;
                    case MOVIE:
                        Movie movie = (Movie) entity;
                        if (movie != null) {
                            controller.startEditing(movie);
                            form = new MovieForm(controller, movie);
                        }
                        break;
                    case CHARACTER:
                        Персонаж character = (Персонаж) entity;
                        if (character != null) {
                            controller.startEditing(character);
                            form = new CharacterForm(controller, character);
                        }
                        break;
                    case DIRECTOR:
                        Director director = (Director) entity;
                        if (director != null) {
                            controller.startEditing(director);
                            form = new DirectorForm(controller, director);
                        }
                        break;
                    case ACTOR:
                        Actor actor = (Actor) entity;
                        if (actor != null) {
                            controller.startEditing(actor);
                            form = new ActorForm(controller, actor);
                        }
                        break;
                    default:
                        break;
                }
            } catch (IOException | JAXBException e) {
                JOptionPane.showMessageDialog(null, "Не удалось выполнить операцию");
            }
            if (form != null) {
                form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                form.setVisible(true);
            }
        }
    };

    public MainForm() {
        try {
            controller = new Controller(messageListener, entityListener);            
            initComponents();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Не удалось выполнить операцию");
            System.exit(1);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entitiesComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        entitiesTable = new javax.swing.JTable();
        deleteButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Movies");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        entitiesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Movies", "Actors", "Characters", "Directors" }));
        entitiesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entitiesComboBoxActionPerformed(evt);
            }
        });

        entitiesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(entitiesTable);

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(refreshButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(entitiesComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(addButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(editButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(deleteButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(entitiesComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(deleteButton)
                    .add(editButton)
                    .add(addButton)
                    .add(refreshButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void entitiesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entitiesComboBoxActionPerformed
        String selectedItem = entitiesComboBox.getSelectedItem().toString();
        EntityType type = EntityType.of(selectedItem);
        try {
            controller.requestEntities(type);
        } catch (IOException | JAXBException e) {
            JOptionPane.showMessageDialog(this, "Не удалось выполнить операцию");
        }
    }//GEN-LAST:event_entitiesComboBoxActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String selectedItem = entitiesComboBox.getSelectedItem().toString();
        EntityType type = EntityType.of(selectedItem);
        JFrame form = null;
        switch (type) {
            case MOVIE:
                form = new MovieForm(controller, null);
                break;
            case CHARACTER:
                form = new CharacterForm(controller, null);
                break;
            case DIRECTOR:
                form = new DirectorForm(controller, null);
                break;
            case ACTOR:
                form = new ActorForm(controller, null);
                break;
            default:
                break;
        }
        if (form != null) {
            form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            form.setVisible(true);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        String selectedItem = entitiesComboBox.getSelectedItem().toString();
        EntityType type = EntityType.of(selectedItem);
        int selectedRow = entitiesTable.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        String id = entitiesTable.getValueAt(selectedRow, 0).toString();
        try {
            controller.requestEntityById(type, id);
        } catch (IOException | JAXBException e) {
            JOptionPane.showMessageDialog(this, "Не удалось выполнить операцию");
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        controller.setDataListener(dataListener);
        entitiesComboBoxActionPerformed(null);
    }//GEN-LAST:event_formWindowGainedFocus

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int selectedRow = entitiesTable.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        String selectedItem = entitiesComboBox.getSelectedItem().toString();
        EntityType type = EntityType.of(selectedItem);
        String entityId = entitiesTable.getValueAt(selectedRow, 0).toString();
        try {
            controller.removeEntity(type, entityId);
            entitiesComboBoxActionPerformed(null);
        } catch (IOException | JAXBException e) {
            JOptionPane.showMessageDialog(this, "Не удалось выполнить операцию");
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        controller.refreshData();
        entitiesComboBoxActionPerformed(evt);
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            controller.dispose();
        } catch (IOException ex) {
             JOptionPane.showMessageDialog(this, "Не удалось выполнить операцию");
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels = javax.swing.UIManager.getInstalledLookAndFeels();
            for (int idx = 0; idx < installedLookAndFeels.length; idx++) {
                if ("Nimbus".equals(installedLookAndFeels[idx].getName())) {
                    javax.swing.UIManager.setLookAndFeel(installedLookAndFeels[idx].getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JComboBox entitiesComboBox;
    private javax.swing.JTable entitiesTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshButton;
    // End of variables declaration//GEN-END:variables

}
