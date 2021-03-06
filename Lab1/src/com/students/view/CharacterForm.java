/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.view;

import com.students.controller.Controller;
import com.students.entity.Actor;
import com.students.entity.Movie;
import com.students.entity.Character;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author pushi_000
 */
public class CharacterForm extends javax.swing.JFrame {

    private Character character;

    private boolean newCharacter = false;

    private final Controller controller;

    private Movie currentMovie;

    private List<Movie> otherMovies;

    private Actor currentActor;

    private List<Actor> otherActors;

    public CharacterForm(Controller controller, Character character) {
        this.controller = controller;
        this.character = character;
        initComponents();
        if (character != null) {
            fillForm();
        } else {
            this.character = new Character();
            this.newCharacter = true;
        }
        fillOtherLists();
    }

    private void fillForm() {
        nameTextField.setText(character.getName());
        descriptionTextField.setText(character.getDescription());
        if (character.getActor() != null && character.getMovie() != null) {
            actorTextField.setText(character.getActor().getName());
            movieTextField.setText(character.getMovie().getName());
            currentActor = character.getActor();
            currentMovie = character.getMovie();
        }

    }

    private void fillOtherLists() {
        otherMovies = new LinkedList<>(controller.getMovies());
        otherMovies.remove(currentMovie);
        DefaultListModel otherMovieListModel = new DefaultListModel();
        otherMovies.stream().forEach((movie) -> otherMovieListModel.addElement(movie.getName()));
        otherMoviesList.setModel(otherMovieListModel);

        otherActors = new LinkedList<>(controller.getActors());
        otherActors.remove(currentActor);
        DefaultListModel otherActorsListModel = new DefaultListModel();
        otherActors.stream().forEach((actor) -> otherActorsListModel.addElement(actor.getName()));
        otherActorsList.setModel(otherActorsListModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        otherActorsList = new javax.swing.JList();
        actorButtonAdd = new javax.swing.JButton();
        actorButtonDelete = new javax.swing.JButton();
        actorTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        otherMoviesList = new javax.swing.JList();
        movieButtonAdd = new javax.swing.JButton();
        movieButtonDelete = new javax.swing.JButton();
        movieTextField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        nameTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Name");

        jLabel2.setText("Description");

        jLabel11.setText("Current actor");

        jLabel12.setText("Other actors");

        jScrollPane8.setMaximumSize(new java.awt.Dimension(100, 88));
        jScrollPane8.setMinimumSize(new java.awt.Dimension(100, 88));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(100, 88));

        otherActorsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(otherActorsList);

        actorButtonAdd.setText("Add");
        actorButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actorButtonAddActionPerformed(evt);
            }
        });

        actorButtonDelete.setText("Delete");
        actorButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actorButtonDeleteActionPerformed(evt);
            }
        });

        actorTextField.setEditable(false);

        jLabel13.setText("Current movie");

        jLabel14.setText("Other movies");

        jScrollPane9.setMaximumSize(new java.awt.Dimension(100, 88));
        jScrollPane9.setMinimumSize(new java.awt.Dimension(100, 88));
        jScrollPane9.setPreferredSize(new java.awt.Dimension(100, 88));

        otherMoviesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane9.setViewportView(otherMoviesList);

        movieButtonAdd.setText("Add");
        movieButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movieButtonAddActionPerformed(evt);
            }
        });

        movieButtonDelete.setText("Delete");
        movieButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movieButtonDeleteActionPerformed(evt);
            }
        });

        movieTextField.setEditable(false);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(actorButtonAdd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(actorButtonDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(actorTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13)
                            .addComponent(movieButtonAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(movieButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(movieTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(movieTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(movieButtonAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(movieButtonDelete))
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveButton)
                            .addComponent(cancelButton))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(actorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(actorButtonAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(actorButtonDelete))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(103, 103, 103))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (currentActor != null && currentMovie != null) {
            character.setName(nameTextField.getText());
            character.setDescription(descriptionTextField.getText());
            character.setMovie(currentMovie);
            character.setActor(currentActor);
            if (newCharacter) {
                controller.addCharacter(character);
            } else {
                controller.editCharacter(character);
            }
            setVisible(false);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Актер или фильм не указан");
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void actorButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actorButtonAddActionPerformed
        if (currentActor == null) {
            int index = otherActorsList.getSelectedIndex();
            if (index >= 0) {
                currentActor = otherActors.remove(index);
                actorTextField.setText(currentActor.getName());
                fillOtherLists();
            }
        }
    }//GEN-LAST:event_actorButtonAddActionPerformed

    private void actorButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actorButtonDeleteActionPerformed
        currentActor = null;
        actorTextField.setText(null);
        fillOtherLists();
    }//GEN-LAST:event_actorButtonDeleteActionPerformed

    private void movieButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movieButtonAddActionPerformed
        if (currentMovie == null) {
            int index = otherMoviesList.getSelectedIndex();
            if (index >= 0) {
                currentMovie = otherMovies.remove(index);
                movieTextField.setText(currentMovie.getName());
                fillOtherLists();
            }
        }
    }//GEN-LAST:event_movieButtonAddActionPerformed

    private void movieButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movieButtonDeleteActionPerformed
        currentMovie = null;
        movieTextField.setText(null);
        fillOtherLists();
    }//GEN-LAST:event_movieButtonDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actorButtonAdd;
    private javax.swing.JButton actorButtonDelete;
    private javax.swing.JTextField actorTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JButton movieButtonAdd;
    private javax.swing.JButton movieButtonDelete;
    private javax.swing.JTextField movieTextField;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JList otherActorsList;
    private javax.swing.JList otherMoviesList;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
