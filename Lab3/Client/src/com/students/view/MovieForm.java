/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.view;

import com.students.controller.Controller;
import com.students.controller.ListOfEntitiesListener;
import com.students.entity.BaseEntity;
import com.students.entity.Персонаж;
import com.students.entity.Director;
import com.students.entity.EntityType;
import com.students.entity.Movie;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

/**
 *
 * @author pushi_000
 */
public class MovieForm extends javax.swing.JFrame {

    private Movie movie;

    private boolean newMovie = false;

    private final Controller controller;

    private final List<Персонаж> currentCharacters = new LinkedList<>();

    private List<Персонаж> otherCharacters;

    private final List<Director> currentDirectors = new LinkedList<>();

    private List<Director> otherDirectors;

    public MovieForm(Controller controller, Movie movie) {
        this.controller = controller;
        controller.setDataListener(new ListOfEntitiesListener() {

            @Override
            public void onDataReceive(EntityType type, List<? extends BaseEntity> entities) {
                if (type == EntityType.CHARACTER) {
                    otherCharacters = (List<Персонаж>) entities;
                    otherCharacters.removeAll(currentCharacters);
                    DefaultListModel otherCharacterListModel = new DefaultListModel();
                    otherCharacters.stream().forEach((character) -> otherCharacterListModel.addElement(character.getName()));
                    otherCharactersList.setModel(otherCharacterListModel);
                } else if (type == EntityType.DIRECTOR) {
                    otherDirectors = (List<Director>) entities;
                    otherDirectors.removeAll(currentDirectors);
                    DefaultListModel otherDirectorsListModel = new DefaultListModel();
                    otherDirectors.stream().forEach((director) -> otherDirectorsListModel.addElement(director.getName()));
                    otherDirectorsList.setModel(otherDirectorsListModel);
                }
            }
        });
        this.movie = movie;
        initComponents();
        if (movie != null) {
            fillForm();
        } else {
            this.movie = new Movie();
            this.newMovie = true;
        }
        fillOtherLists();
    }

    private void fillForm() {
        nameTextField.setText(movie.getName());
        releaseDateTextField.setText(movie.getReleaseDate().toString());
        durationTextField.setText(Integer.toString(movie.getDuration()));
        budgetTextField.setText(Long.toString(movie.getBudget()));
        descriptionTextField.setText(movie.getDescription());
        genresTextField.setText(movie.getGenres());
        countriesTestField.setText(movie.getCountries());

        DefaultListModel currentCharacterListModel = new DefaultListModel();
        for (Персонаж character : movie.getCharacters()) {
            currentCharacters.add(character);
            currentCharacterListModel.addElement(character.getName());
        }
        currentCharactersList.setModel(currentCharacterListModel);

        DefaultListModel currentDirectorsListModel = new DefaultListModel();
        for (Director director : movie.getDirectors()) {
            currentDirectors.add(director);
            currentDirectorsListModel.addElement(director.getName());
        }
        currentDirectorsList.setModel(currentDirectorsListModel);
    }

    private void fillCurrentLists() {
        DefaultListModel currentCharacterListModel = new DefaultListModel();
        for (Персонаж character : currentCharacters) {
            currentCharacterListModel.addElement(character.getName());
        }
        currentCharactersList.setModel(currentCharacterListModel);

        DefaultListModel currentDirectorsListModel = new DefaultListModel();
        for (Director director : currentDirectors) {
            currentDirectorsListModel.addElement(director.getName());
        }
        currentDirectorsList.setModel(currentDirectorsListModel);
    }

    private void fillOtherLists() {
        try {
            controller.requestEntities(EntityType.CHARACTER);
            controller.requestEntities(EntityType.DIRECTOR);
        } catch (IOException | JAXBException e) {
            JOptionPane.showMessageDialog(this, "Не удалось выполнить операцию");            
            this.setVisible(false);
            this.dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane9 = new javax.swing.JScrollPane();
        currentCharactersList = new javax.swing.JList();
        jScrollPane10 = new javax.swing.JScrollPane();
        otherCharactersList = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        characterButtonAdd = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        characterButtonDelete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        durationTextField = new javax.swing.JTextField();
        budgetTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        currentDirectorsList = new javax.swing.JList();
        jScrollPane8 = new javax.swing.JScrollPane();
        otherDirectorsList = new javax.swing.JList();
        directorButtonAdd = new javax.swing.JButton();
        directorButtonDelete = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        releaseDateTextField = new javax.swing.JTextField();
        nameTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        countriesTestField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        genresTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane9.setMaximumSize(new java.awt.Dimension(100, 130));
        jScrollPane9.setMinimumSize(new java.awt.Dimension(100, 130));
        jScrollPane9.setPreferredSize(new java.awt.Dimension(100, 130));

        currentCharactersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane9.setViewportView(currentCharactersList);

        jScrollPane10.setMaximumSize(new java.awt.Dimension(100, 130));
        jScrollPane10.setMinimumSize(new java.awt.Dimension(100, 130));
        jScrollPane10.setPreferredSize(new java.awt.Dimension(100, 130));

        otherCharactersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane10.setViewportView(otherCharactersList);

        jLabel3.setText("Duration");

        characterButtonAdd.setText("Add");
        characterButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                characterButtonAddActionPerformed(evt);
            }
        });

        jLabel4.setText("Budget");

        characterButtonDelete.setText("Delete");
        characterButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                characterButtonDeleteActionPerformed(evt);
            }
        });

        jLabel5.setText("Description");

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

        jLabel11.setText("Current directors");

        jLabel12.setText("Other directors");

        jScrollPane7.setMaximumSize(new java.awt.Dimension(100, 130));
        jScrollPane7.setMinimumSize(new java.awt.Dimension(100, 130));
        jScrollPane7.setPreferredSize(new java.awt.Dimension(100, 130));

        currentDirectorsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(currentDirectorsList);

        jScrollPane8.setMaximumSize(new java.awt.Dimension(100, 130));
        jScrollPane8.setMinimumSize(new java.awt.Dimension(100, 130));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(100, 130));

        otherDirectorsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        otherDirectorsList.setMaximumSize(new java.awt.Dimension(100, 130));
        otherDirectorsList.setMinimumSize(new java.awt.Dimension(100, 130));
        jScrollPane8.setViewportView(otherDirectorsList);

        directorButtonAdd.setText("Add");
        directorButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directorButtonAddActionPerformed(evt);
            }
        });

        directorButtonDelete.setText("Delete");
        directorButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directorButtonDeleteActionPerformed(evt);
            }
        });

        jLabel13.setText("Current characters");

        jLabel14.setText("Other characters");

        jLabel1.setText("Name");

        jLabel2.setText("Release Date");

        jLabel6.setText("Countries");

        jLabel7.setText("Genres");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(budgetTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(durationTextField)
                            .addComponent(releaseDateTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameTextField)
                            .addComponent(descriptionTextField)
                            .addComponent(countriesTestField)
                            .addComponent(genresTextField))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(directorButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(directorButtonDelete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(characterButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(characterButtonDelete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(characterButtonAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(characterButtonDelete))
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(releaseDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(durationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(budgetTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(countriesTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(genresTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelButton))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(directorButtonAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(directorButtonDelete))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        setVisible(false);        
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        String alertMessage = "В данные поля были введены некорректные данные:\n";

        String date = releaseDateTextField.getText();
        LocalDate ldt = null;
        try {
            ldt = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            alertMessage += "Release Date\n";
            System.out.println(e.getMessage());
        }

        String durationString = durationTextField.getText();
        Integer duration = null;
        try {
            duration = Integer.parseInt(durationString);
        } catch (NumberFormatException e) {
            alertMessage += "Duration\n";
        }

        String budgetString = budgetTextField.getText();
        Integer budget = null;
        try {
            budget = Integer.parseInt(budgetString);
        } catch (NumberFormatException e) {
            alertMessage += "Budget\n";
        }

        if (ldt != null && duration != null && budget != null) {
            movie.setName(nameTextField.getText());
            movie.setBudget(budget);
            movie.setDescription(descriptionTextField.getText());
            movie.setDuration(duration);
            movie.setReleaseDate(ldt);

            movie.setCharacters(currentCharacters);
            movie.setCountries(countriesTestField.getText());
            movie.setDirectors(currentDirectors);
            movie.setGenres(genresTextField.getText());

            try {
                if (newMovie) {
                    controller.addEntity(movie);
                } else {
                    controller.editEntity(movie);
                }
            } catch (IOException | JAXBException e) {
                alertMessage = "Не удалось выполнить операцию";
                JOptionPane.showMessageDialog(this, alertMessage);
            }
            setVisible(false);            
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, alertMessage);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    @Override
    public void dispose() {
        super.dispose(); 
        try {
            controller.finishEditing(movie);
        } catch (IOException | JAXBException e) {
            JOptionPane.showMessageDialog(this, "Не удалось выполнить операцию");
        }
    }    
    
    private void characterButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_characterButtonAddActionPerformed
        int index = otherCharactersList.getSelectedIndex();
        if (index >= 0) {
            Персонаж character = otherCharacters.remove(index);
            currentCharacters.add(character);
            fillCurrentLists();
            fillOtherLists();
        }
    }//GEN-LAST:event_characterButtonAddActionPerformed

    private void characterButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_characterButtonDeleteActionPerformed
        int index = currentCharactersList.getSelectedIndex();
        if (index >= 0) {
            Персонаж character = currentCharacters.remove(index);
            otherCharacters.add(character);
            fillCurrentLists();
            fillOtherLists();
        }
    }//GEN-LAST:event_characterButtonDeleteActionPerformed

    private void directorButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directorButtonAddActionPerformed
        int index = otherDirectorsList.getSelectedIndex();
        if (index >= 0) {
            Director director = otherDirectors.remove(index);
            currentDirectors.add(director);
            fillCurrentLists();
            fillOtherLists();
        }
    }//GEN-LAST:event_directorButtonAddActionPerformed

    private void directorButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directorButtonDeleteActionPerformed
        int index = currentDirectorsList.getSelectedIndex();
        if (index >= 0) {
            Director director = currentDirectors.remove(index);
            otherDirectors.add(director);
            fillCurrentLists();
            fillOtherLists();
        }
    }//GEN-LAST:event_directorButtonDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField budgetTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton characterButtonAdd;
    private javax.swing.JButton characterButtonDelete;
    private javax.swing.JTextField countriesTestField;
    private javax.swing.JList currentCharactersList;
    private javax.swing.JList currentDirectorsList;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JButton directorButtonAdd;
    private javax.swing.JButton directorButtonDelete;
    private javax.swing.JTextField durationTextField;
    private javax.swing.JTextField genresTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JList otherCharactersList;
    private javax.swing.JList otherDirectorsList;
    private javax.swing.JTextField releaseDateTextField;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
