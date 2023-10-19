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

@SuppressWarnings("unused")
public class Relatorios extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;

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

	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/relatorio.png")));
		setTitle("Relatórios");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JButton btnClientes = new JButton("");
		btnClientes.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		btnClientes.setContentAreaFilled(false);
		btnClientes.setToolTipText("Clientes PDF");
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/clientOS.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setBounds(30, 50, 138, 138);
		getContentPane().add(btnClientes);

		JButton btnServicos = new JButton("");
		btnServicos.setContentAreaFilled(false);
		btnServicos.setToolTipText("Relatórios PDF");
		btnServicos.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/relatorioOS2.png")));
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioServicos();
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setBounds(300, 50, 138, 138);
		getContentPane().add(btnServicos);

		JLabel lblClientes = new JLabel("Clientes");
		lblClientes.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblClientes.setBounds(50, 190, 130, 54);
		getContentPane().add(lblClientes);

		JLabel lblRelatorios = new JLabel("Relatórios");
		lblRelatorios.setToolTipText("Relatórios pdf");
		lblRelatorios.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblRelatorios.setBounds(315, 190, 138, 54);
		getContentPane().add(lblRelatorios);

		JButton btnReposicao = new JButton("");
		btnReposicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioReposicao();
			}
		});
		btnReposicao.setIcon(new ImageIcon(Relatorios.class.getResource("/img/repository.png")));
		btnReposicao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReposicao.setToolTipText("Reposição PDF");
		btnReposicao.setContentAreaFilled(false);
		btnReposicao.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		btnReposicao.setBounds(600, 50, 138, 138);
		getContentPane().add(btnReposicao);

		JLabel lblReposicao = new JLabel("Reposição");
		lblReposicao.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblReposicao.setBounds(610, 190, 234, 54);
		getContentPane().add(lblReposicao);

		JButton btnValidade = new JButton("");
		btnValidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioValidade();
			}
		});
		btnValidade.setIcon(new ImageIcon(Relatorios.class.getResource("/img/date val.png")));
		btnValidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnValidade.setToolTipText("Validade PDF");
		btnValidade.setContentAreaFilled(false);
		btnValidade.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		btnValidade.setBounds(30, 250, 138, 138);
		getContentPane().add(btnValidade);

		JButton btnPatrimonio = new JButton("");
		btnPatrimonio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioPatrimonio();
			}
		});
		btnPatrimonio.setIcon(new ImageIcon(Relatorios.class.getResource("/img/patrimony.png")));
		btnPatrimonio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPatrimonio.setToolTipText("Patrimônio PDF");
		btnPatrimonio.setContentAreaFilled(false);
		btnPatrimonio.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		btnPatrimonio.setBounds(300, 250, 138, 138);
		getContentPane().add(btnPatrimonio);

		JLabel lblValidade = new JLabel("Validade");
		lblValidade.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblValidade.setBounds(50, 390, 213, 54);
		getContentPane().add(lblValidade);

		JLabel lblPatrimonio = new JLabel("Patrimônio");
		lblPatrimonio.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblPatrimonio.setBounds(315, 390, 252, 54);
		getContentPane().add(lblPatrimonio);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(128, 64, 64));
		lblNewLabel_4.setBounds(0, 480, 784, 91);
		getContentPane().add(lblNewLabel_4);

		JButton btnFornecedores = new JButton("");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioFornecedores();
			}
		});
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setIcon(new ImageIcon(Relatorios.class.getResource("/img/Fornecedores.png")));
		btnFornecedores.setToolTipText("Fornecedores PDF");
		btnFornecedores.setContentAreaFilled(false);
		btnFornecedores.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0),
				new Color(0, 0, 0), new Color(0, 0, 0)));
		btnFornecedores.setBounds(600, 250, 138, 138);
		getContentPane().add(btnFornecedores);

		JLabel lblFornecedores = new JLabel("Fornecedores");
		lblFornecedores.setFont(new Font("Swis721 Hv BT", Font.BOLD, 22));
		lblFornecedores.setBounds(590, 390, 193, 54);
		getContentPane().add(lblFornecedores);
	}

	private void relatorioClientes() {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" "));
			String readClientes = "select nome,fone,email from clientes order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(3);
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioServicos() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("servico.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Serviços:"));
			document.add(new Paragraph(" "));
			String readServicos = "select servicos.os,servicos.dataos,servicos.equipamento,servicos.defeito,servicos.valor,clientes.nome from servicos inner join clientes on servicos.idcli = clientes.idcli";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readServicos);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(6);
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
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("servico.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioReposicao() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("reposicao.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("reposição:"));
			document.add(new Paragraph(" "));
			String readServicos = "select nome,estoque,estoquemin from produtos order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readServicos);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(4);
				PdfPCell col1 = new PdfPCell(new Paragraph("Produto"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Estoque"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Estoque mínimo"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Precisa repor?"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell("[  ] SIM [  ]  NÃO");
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("reposicao.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioValidade() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("validade.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Validade:"));
			document.add(new Paragraph(" "));
			String readServicos = "select nome,dataent,dataval from produtos order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readServicos);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(3);
				PdfPCell col1 = new PdfPCell(new Paragraph("Produto"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Data de entrada"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Data de validade"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("validade.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioFornecedores() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("fornecedores.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Fornecedores:"));
			document.add(new Paragraph(" "));
			String readServicos = "select razao,fantasia,cnpj,fone,email from fornecedores order by razao";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readServicos);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(5);
				PdfPCell col1 = new PdfPCell(new Paragraph("Razão"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fantasia"));
				PdfPCell col3 = new PdfPCell(new Paragraph("CNPJ"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Telefone"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Email"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("fornecedores.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioPatrimonio() {

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream("estoque.pdf"));
			document.open();
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Estoque:"));
			document.add(new Paragraph(" "));
			String readClientes = "select codigo as código, nome as produto, date_format(dataval, '%d/%m/%Y') as validade, estoque, estoquemin as estóque_mínimo from produtos where estoque < estoquemin;";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				PdfPTable tabela = new PdfPTable(5);
				PdfPCell col1 = new PdfPCell(new Paragraph("código: "));
				PdfPCell col2 = new PdfPCell(new Paragraph("produto: "));
				PdfPCell col3 = new PdfPCell(new Paragraph("validade: "));
				PdfPCell col4 = new PdfPCell(new Paragraph("estoque: "));
				PdfPCell col5 = new PdfPCell(new Paragraph("estoque mínimo: "));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}
				document.add(tabela);
				document.add(new Paragraph("Validade:"));
				document.add(new Paragraph(" "));
				String read = "select codigo as código, nome as produto, date_format(dataval, '%d/%m/%Y') as validade from produtos where dataval < dataent;";
				pst = con.prepareStatement(read);
				rs = pst.executeQuery();
				PdfPTable tabela2 = new PdfPTable(3);
				PdfPCell col6 = new PdfPCell(new Paragraph("código: "));
				PdfPCell col7 = new PdfPCell(new Paragraph("produto: "));
				PdfPCell col8 = new PdfPCell(new Paragraph("validade: "));
				tabela2.addCell(col6);
				tabela2.addCell(col7);
				tabela2.addCell(col8);
				while (rs.next()) {
					tabela2.addCell(rs.getString(1));
					tabela2.addCell(rs.getString(2));
					tabela2.addCell(rs.getString(3));
				}
				document.add(tabela2);
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Patrimônio (Custo):"));
				document.add(new Paragraph(" "));
				String read2 = "select sum(custo * estoque) as Total from produtos";
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();
				PdfPTable tabela3 = new PdfPTable(1);
				PdfPCell col12 = new PdfPCell(new Paragraph("Patrimônio custo: "));
				tabela3.addCell(col12);
				while (rs.next()) {
					tabela3.addCell(rs.getString(1));
				}
				document.add(tabela3);
				document.add(new Paragraph(" "));
				document.add(new Paragraph("Patrimônio (venda):"));
				document.add(new Paragraph(" "));
				String readVenda = "select sum((custo + (custo * lucro)/100) * estoque) as total from produtos";
				pst = con.prepareStatement(readVenda);
				rs = pst.executeQuery();
				PdfPTable tabela4 = new PdfPTable(1);
				PdfPCell col43 = new PdfPCell(new Paragraph("Patrimônio venda: "));
				tabela4.addCell(col43);
				while (rs.next()) {
					tabela4.addCell(rs.getString(1));
				}
				document.add(tabela4);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("estoque.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
