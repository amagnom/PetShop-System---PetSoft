/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Modelos.Caixa;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Atlas
 */
public class ControleCaixa {

    //banco
    public void caixaTotal(Caixa caixa) throws MessagingException {
        Conexao conexao = new Conexao();

        try {
            String query = "INSERT INTO Caixa  (saldo,contasReceber,contasPagar)"
                    + "VALUES(?,?,?)";
            PreparedStatement ps = conexao.getConnection().prepareStatement(query);

            ps.setFloat(1, caixa.getSaldo());
            ps.setFloat(2, caixa.getContasReceber());
            ps.setFloat(3, caixa.getContasPagar());

            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ControleFornecedor.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conexao.closeConnection();
        }

    }

}
