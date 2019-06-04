/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telasPet;

import Controle.Conexao;
import Controle.ControleAgenda;
import Modelos.Agenda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Atlas
 */
public class AgendamentoConsultaAlterarDados extends javax.swing.JFrame {

    /**
     * Creates new form AgendamentoConsultaAlterarDados
     */
    public AgendamentoConsultaAlterarDados() {
        initComponents();
        Conexao conexao = new Conexao();
        String cpfCnpj = "";
        String animal = "";
        String nome = "";
        ControleAgenda controle = new ControleAgenda();
        ArrayList<Agenda> listaAgenda = controle.ListaAgendas("where concluido = false");
        String[] tblHead = {"Tipo", "Cliente", "CPF", "Animal", "Data", "Hora", "Valor", "Concluído"};
        DefaultTableModel dtm = new DefaultTableModel(tblHead, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };
        dtm.addRow(tblHead);
        String concluido = "", tipo = "";
        for (int i = 0; i < listaAgenda.size(); i++) {
            if (listaAgenda.get(i).isConcluido()) {
                concluido = "Sim";
            } else {
                concluido = "Não";
            }
            if (listaAgenda.get(i).getTipo() == 'B') {
                tipo = "Banho";
            } else {
                tipo = "Consulta";
            }
               String query = "select nomeFantasia,cpfcnpj from cliente where id=?";
            try {
                PreparedStatement ps = conexao.getConnection().prepareStatement(query);
                ps.setInt(1, listaAgenda.get(i).getIdCliente());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                   nome = rs.getString("nomeFantasia");
                    cpfCnpj = rs.getString("cpfcnpj");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgendamentoConsulta.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                conexao.closeConnection();
            }
            query = "select nome from pet where idCliente = ?";
            try {
                PreparedStatement ps = conexao.getConnection().prepareStatement(query);
                ps.setInt(1, listaAgenda.get(i).getIdCliente());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    animal = rs.getString("nome");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgendamentoConsulta.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                conexao.closeConnection();
            }
            dtm.addRow(new String[]{tipo, nome,cpfCnpj, animal,
                listaAgenda.get(i).getData(),
                listaAgenda.get(i).getHora(),
                String.valueOf(listaAgenda.get(i).getValor()), concluido
            });
        }
        JTable table = new JTable(dtm);

        painel.add(table);

        table.addMouseListener(
                new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt
            ) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    int opcao = JOptionPane.showConfirmDialog(painel,
                            "Deseja alterar a agenda?",
                            "Sim ou não?", JOptionPane.YES_NO_OPTION);
                    boolean flag;
                    flag = opcao == JOptionPane.YES_OPTION;
                    if (flag) {
                        //   listaAgenda.get(row - 1).setConcluido(true);
                        String nome = dtm.getValueAt(table.getSelectedRow(), 1).toString();
                        String nomePet = dtm.getValueAt(table.getSelectedRow(), 3).toString();
                        String cpf = dtm.getValueAt(table.getSelectedRow(), 2).toString();
                        if (listaAgenda.get(row - 1).getTipo() == 'B') {
                            new AgendamentoBanho(listaAgenda.get(row - 1), nome, nomePet, cpf).setVisible(true);

                        } else {
                            new AgendamentoConsulta(listaAgenda.get(row - 1), nome, nomePet, cpf).setVisible(true);
                        }
                        dispose();
                    }
                }
            }
        }
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        painel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Alterar Agenda");

        painel.setLayout(new java.awt.CardLayout());
        jScrollPane1.setViewportView(painel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(jLabel2)
                .addContainerGap(244, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgendamentoConsultaAlterarDados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgendamentoConsultaAlterarDados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgendamentoConsultaAlterarDados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgendamentoConsultaAlterarDados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgendamentoConsultaAlterarDados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel painel;
    // End of variables declaration//GEN-END:variables
}
