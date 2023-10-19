package view;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JDialog;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;

import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Color;

public class Relatorios extends JDialog {

	// Objetos JDB
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios dialog = new Relatorios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/relatorio.png")));
		setTitle("Relatórios");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 511, 331);
		getContentPane().setLayout(null);

		JButton btnClientes = new JButton("");
		btnClientes.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		btnClientes.setContentAreaFilled(false);
		btnClientes.setToolTipText("Imprimir Clientes");
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/clientOS.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setBounds(30, 50, 128, 128);
		getContentPane().add(btnClientes);

		JButton btnServicos = new JButton("");
		btnServicos.setContentAreaFilled(false);
		btnServicos.setToolTipText("Imprimir Relatórios");
		btnServicos.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/relatorioOS2.png")));
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioServicos();
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setBounds(325, 50, 128, 128);
		getContentPane().add(btnServicos);
		
		JLabel lblClientes = new JLabel("Imprimir Clientes");
		lblClientes.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblClientes.setBounds(10, 189, 213, 54);
		getContentPane().add(lblClientes);
		
		JLabel lblRelatorios = new JLabel("Imprimir Relatórios");
		lblRelatorios.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblRelatorios.setBounds(262, 189, 223, 54);
		getContentPane().add(lblRelatorios);

	} // Fim do construtor

	private void relatorioClientes() {
		//instanciar um objeto para construir a página pdf
		Document document = new Document();
		//configurar como A4 e modo paisagem
		//document.setPageSize(PageSize.A4.rotate());
		//gerar o documento pdf
		try {
			//criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			//abrir o documento (formatar e inserir o conteúdo)
			document.open();
			//adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			//adicionar um páragrafo
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" ")); //pular uma linha
			//----------------------------------------------------------
			//query (instrução sql para gerar o relatório de clientes)
			String readClientes = "select nome,fone,email from clientes order by nome";
			try {
				//abrir a conexão com o banco
				con = dao.conectar();
				//preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readClientes);
				//obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				//atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(3); //(2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {
					//popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}				
				//adicionar a tabela ao documento pdf
				document.add(tabela);
				//fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		//fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		//Abrir o desktop do sistema operacional e usar o leitor padrão
		//de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}		
	}

	private void relatorioServicos() {
		// instanciar um objeto para construir a página pdf
		Document document = new Document();
		// configurar como A4 e modo paisagem
		document.setPageSize(PageSize.A4.rotate());
		// gerar o documento pdf
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("servico.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			// adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			// adicionar um páragrafo
			document.add(new Paragraph("Serviços:"));
			document.add(new Paragraph(" ")); // pular uma linha
			// ----------------------------------------------------------
			// query (instrução sql para gerar o relatório de clientes)
			String readServicos = "select servicos.os,servicos.dataos,servicos.equipamento,servicos.defeito,servicos.valor,clientes.nome from servicos inner join clientes on servicos.idcli = clientes.idcli";
			try {
				// abrir a conexão com o banco
				con = dao.conectar();
				// preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readServicos);
				// obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				// atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(6); // (2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("DataOS"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Equipamento"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Defeito"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Valor"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Nome"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				while (rs.next()) {
					// popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}
				// adicionar a tabela ao documento pdf
				document.add(tabela);
				// fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("servico.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
} // Fim do código
