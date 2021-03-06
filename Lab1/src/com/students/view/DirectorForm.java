/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.students.view;

import com.students.controller.Controller;
import com.students.entity.Director;
import com.students.entity.Movie;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author pushi_000
 */
public class DirectorForm extends javax.swing.JFrame {

    private Director director;

    private boolean newDirector = false;

    private final Controller controller;

    private final List<Movie> currentMovies = new LinkedList<>();

    private List<Movie> otherMovies;

    /**
     * Creates new form ActorForm
     */
    public DirectorForm(Controller controller, Director director) {
        this.controller = controller;
        this.director = director;
        initComponents();
        if (director != null) {
            fillForm();
        } else {
            this.director = new Director();
            this.newDirector = true;
        }
        fillOtherLists();
    }

    private void fillForm() {
        nameTextField.setText(director.getName());
        birthDateTextField.setText(director.getBirthDate().toString());
        birthCountryTextField.setText(director.getBirthCountry());

        DefaultListModel currentMoviesListModel = new DefaultListModel();
        for (Movie movie : director.getMovies()) {
            currentMovies.add(movie);
            currentMoviesListModel.addElement(movie.getName());
        }
        currentMoviesList.setModel(currentMoviesListModel);
    }

    private void fillCurrentLists() {
        DefaultListModel currentMoviesListModel = new DefaultListModel();
        for (Movie movie : currentMovies) {
            currentMoviesListModel.addElement(movie.getName());
        }
        currentMoviesList.setModel(currentMoviesListModel);
    }

    private void fillOtherLists() {
        otherMovies = new LinkedList<>(controller.getMovies());
        otherMovies.removeAll(currentMovies);
        DefaultListModel otherMovieListModel = new DefaultListModel();
        otherMovies.stream().forEach((movie) -> otherMovieListModel.addElement(movie.getName()));
        otherMoviesList.setModel(otherMovieListModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        birthCountryTextField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        movieButtonDelete = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        currentMoviesList = new javax.swing.JList();
        nameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        otherMoviesList = new javax.swing.JList();
        birthDateTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        movieButtonAdd = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        movieButtonDelete.setText("Delete");
        movieButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movieButtonDeleteActionPerformed(evt);
            }
        });

        jLabel13.setText("Current movies");

        jLabel14.setText("Other movies");

        jLabel1.setText("Name");

        jScrollPane9.setMaximumSize(new java.awt.Dimension(100, 130));
        jScrollPane9.setMinimumSize(new java.awt.Dimension(100, 130));
        jScrollPane9.setPreferredSize(new java.awt.Dimension(100, 130));

        currentMoviesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane9.setViewportView(currentMoviesList);

        jLabel2.setText("Birth Date");

        jScrollPane10.setMaximumSize(new java.awt.Dimension(100, 130));
        jScrollPane10.setMinimumSize(new java.awt.Dimension(100, 130));
        jScrollPane10.setPreferredSize(new java.awt.Dimension(100, 130));

        otherMoviesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane10.setViewportView(otherMoviesList);

        jLabel3.setText("Birth Country");

        movieButtonAdd.setText("Add");
        movieButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movieButtonAddActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(birthDateTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(birthCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(movieButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(movieButtonDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(birthDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(birthCountryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(movieButtonAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(movieButtonDelete))
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

        String date = birthDateTextField.getText();
        LocalDate ldt = null;
        try {
            ldt = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            alertMessage += "Birth Date\n";
            System.out.println(e.getMessage());
        }

        if (ldt != null) {

            director.setName(nameTextField.getText());
            director.setBirthDate(ldt);
            director.setBirthCountry(birthCountryTextField.getText());
            director.setMovies(currentMovies);

            if (newDirector) {
                controller.addDirector(director);
            } else {
                controller.editDirector(director);
            }
            setVisible(false);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, alertMessage);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void movieButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movieButtonAddActionPerformed
        int index = otherMoviesList.getSelectedIndex();
        if (index >= 0) {
            Movie movie = otherMovies.remove(index);
            currentMovies.add(movie);
            fillCurrentLists();
            fillOtherLists();
        }
    }//GEN-LAST:event_movieButtonAddActionPerformed

    private void movieButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movieButtonDeleteActionPerformed
        int index = currentMoviesList.getSelectedIndex();
        if (index >= 0) {
            Movie movie = currentMovies.remove(index);
            otherMovies.add(movie);
            fillCurrentLists();
            fillOtherLists();
        }
    }//GEN-LAST:event_movieButtonDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField birthCountryTextField;
    private javax.swing.JTextField birthDateTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JList currentMoviesList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JButton movieButtonAdd;
    private javax.swing.JButton movieButtonDelete;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JList otherMoviesList;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

}
