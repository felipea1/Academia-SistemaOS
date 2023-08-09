package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import utils.Validador;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JProgressBar;

public class Servicos extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtID;
	private JTextField txtData;
	private JTextField txtEquipamento;
	private JTextField txtDefeito;
	private JTextField txtValor;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JTextField txtCliente;
	private JScrollPane scrollPane;
	private JList listCli;
	private JButton btnOS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
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
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/OrdemOS.png")));
		setTitle("Serviços");
		setModal(true);
		setBounds(100, 100, 638, 410);
		getContentPane().setLayout(null);

		JLabel lblOS = new JLabel("OS:");
		lblOS.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOS.setBounds(10, 10, 46, 14);
		getContentPane().add(lblOS);

		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtOS.setBounds(49, 7, 149, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblData.setBounds(10, 70, 46, 14);
		getContentPane().add(lblData);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(49, 67, 149, 20);
		getContentPane().add(txtData);
		txtData.setColumns(10);

		JLabel lblEquipamento = new JLabel("Equipamento:");
		lblEquipamento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEquipamento.setBounds(10, 130, 93, 14);
		getContentPane().add(lblEquipamento);

		txtEquipamento = new JTextField();
		txtEquipamento.setBounds(95, 130, 270, 20);
		txtEquipamento.setDocument(new Validador(200));
		getContentPane().add(txtEquipamento);
		txtEquipamento.setColumns(10);

		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Buscar();
			}
		});
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/buscarCli.png")));
		btnBuscar.setBounds(242, 11, 32, 32);
		getContentPane().add(btnBuscar);

		JLabel lblDefeito = new JLabel("Defeito: ");
		lblDefeito.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDefeito.setBounds(10, 190, 61, 14);
		getContentPane().add(lblDefeito);

		txtDefeito = new JTextField();
		txtDefeito.setBounds(95, 187, 270, 20);
		txtDefeito.setDocument(new Validador(200));
		getContentPane().add(txtDefeito);
		txtDefeito.setColumns(10);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblValor.setBounds(10, 250, 46, 14);
		getContentPane().add(lblValor);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setBounds(95, 247, 86, 20);
		txtValor.setDocument(new Validador(200));
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		btnAdicionar = new JButton("");
		btnAdicionar.setToolTipText("Adicionar OS");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/adicionarCli.png")));
		btnAdicionar.setBounds(10, 290, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setToolTipText("Editar OS");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Editar();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editarCli.png")));
		btnEditar.setBounds(110, 290, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setToolTipText("Excluir OS");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Excluir();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/deletarCli.png")));
		btnExcluir.setBounds(210, 290, 64, 64);
		getContentPane().add(btnExcluir);

		JButton btnLimpar = new JButton("");
		btnLimpar.setToolTipText("Limpar campos");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/eraser.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(310, 290, 64, 64);
		getContentPane().add(btnLimpar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(369, 10, 243, 93);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 223, 41);
		panel.add(scrollPane);
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);

		listCli = new JList();
		scrollPane.setViewportView(listCli);
		listCli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		listCli.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtCliente.setBounds(10, 32, 223, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(30, 63, 80, 20);
		panel.add(txtID);
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtID.setColumns(10);

		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(10, 66, 46, 14);
		panel.add(lblID);

		btnOS = new JButton("");
		btnOS.setToolTipText("Imprimir OS");
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setContentAreaFilled(false);
		btnOS.setBorder(null);
		btnOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/PDF2.png")));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnOS.setBounds(410, 290, 64, 64);
		getContentPane().add(btnOS);

	} // Fim do construtor

	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listCli.setModel(modelo);
		String readListaCli = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
				+ "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readListaCli);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtCliente.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarClienteLista() {
		int linha = listCli.getSelectedIndex();
		if (linha >= 0) {
			String readListaCli = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCli);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPane.setVisible(false);
		}
	}

	private void LimparCampos() {
		txtCliente.setText(null);
		txtID.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamento.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnAdicionar.setEnabled(true);
	}

	private void Buscar() {
		// captura do numero da OS sem usar a caixa de texto
		String numOS = JOptionPane.showInputDialog("Numero da OS");

		String read = "select * from servicos where os = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);

			pst.setString(1, numOS);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtEquipamento.setText(rs.getString(3));
				txtDefeito.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtID.setText(rs.getString(6));
				txtID.setEnabled(false);
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnAdicionar.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "OS inexistente");
				btnAdicionar.setEnabled(true);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void Adicionar() {

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o equipamento do Cliente!");
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o tipo de defeito!");
			txtDefeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor da OS!");
			txtDefeito.requestFocus();
		} else {
			String create = "insert into servicos (equipamento,defeito,valor,idcli) values (?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "OS realizada!");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void Editar() {
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o equipamento do Cliente!");
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o tipo de defeito!");
			txtDefeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor da OS!");
			txtDefeito.requestFocus();
		} else {
			String update = "update servicos set equipamento=?,defeito=?,valor=? where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso!");
				LimparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void Excluir() {
		int confirmar = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir a Ordem de Serviço?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirmar == JOptionPane.YES_OPTION) {
			String delete = "delete from servicos where os=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtOS.getText());
				pst.executeUpdate();
				LimparCampos();
				JOptionPane.showMessageDialog(null, "Cliente excluido!");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void imprimirOS() {
		// instanciar objeto para usar os métodos da biblioteca
		Document document = new Document();
		// documento pdf
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			String readOS = "select * from servicos where os = ?";
			// conexão com o banco
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql)
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				// executar a query
				rs = pst.executeQuery();
				// se existir a OS
				if (rs.next()) {
					// document.add(new Paragraph("OS: " + rs.getString(1)));
					Paragraph os = new Paragraph("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);

					Paragraph dataOS = new Paragraph("Data: " + rs.getString(2));
					dataOS.setAlignment(Element.ALIGN_RIGHT);
					document.add(dataOS);

					Paragraph equipamento = new Paragraph("Equipamento: " + rs.getString(3));
					equipamento.setAlignment(Element.ALIGN_LEFT);
					document.add(equipamento);

					Paragraph defeito = new Paragraph("Defeito: " + rs.getString(4));
					defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(defeito);

					Paragraph valor = new Paragraph("Valor: " + rs.getString(5));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);

					// imprimir imagens
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/os2.png"));
					imagem.scaleToFit(128, 128);
					imagem.setAbsolutePosition(20, 300);
					document.add(imagem);
				}
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
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
} // Fim do código
