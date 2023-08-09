package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; 

public class Sobre extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel lblGithub;
	private JLabel lblYoutube;
	private JLabel lblTextoGit;
	private JLabel lblTextoInsta;
	private JLabel lblRepositorio;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				}	catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Sobre() {
		getContentPane().setBackground(SystemColor.text);
		setModal(true);
		setTitle("Sobre");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/9071369_dumbbell_icon.png")));
		setBounds(100, 100, 450, 382);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Target Assistencia");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(127, 11, 151, 26);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Autor: Felipe de Almeida Duarte");
		lblNewLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 59, 214, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sob a licença MIT");
		lblNewLabel_2.setBounds(301, 111, 112, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_3.setBounds(285, 111, 128, 128);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblHalterUm = new JLabel("");
		lblHalterUm.setIcon(new ImageIcon(Sobre.class.getResource("/img/9071369_dumbbell_icon.png")));
		lblHalterUm.setBorder(null);
		lblHalterUm.setBounds(288, 0, 48, 48);
		getContentPane().add(lblHalterUm);
		
		JLabel lblHalterDois = new JLabel("");
		lblHalterDois.setIcon(new ImageIcon(Sobre.class.getResource("/img/9071369_dumbbell_icon.png")));
		lblHalterDois.setBorder(null);
		lblHalterDois.setBounds(69, 0, 48, 48);
		getContentPane().add(lblHalterDois);
		
		lblGithub = new JLabel("");
		lblGithub.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/felipea1");
			}
		});
		lblGithub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGithub.setIcon(new ImageIcon(Sobre.class.getResource("/img/211904_social_github_icon.png")));
		lblGithub.setBorder(null);
		lblGithub.setBounds(10, 229, 48, 48);
		getContentPane().add(lblGithub);
		
		JLabel lblNewLabel_1_1 = new JLabel("Redes sociais:");
		lblNewLabel_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(10, 204, 91, 14);
		getContentPane().add(lblNewLabel_1_1);
		
		lblTextoGit = new JLabel("felipea1");
		lblTextoGit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/felipea1");
			}
		});
		lblTextoGit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTextoGit.setForeground(SystemColor.textHighlight);
		lblTextoGit.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 17));
		lblTextoGit.setBounds(69, 244, 91, 20);
		getContentPane().add(lblTextoGit);
		
		lblYoutube = new JLabel("");
		lblYoutube.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {		
				link("https://www.youtube.com/@yfelpsz2302");
			}
		});
		lblYoutube.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblYoutube.setIcon(new ImageIcon(Sobre.class.getResource("/img/youtube.png")));
		lblYoutube.setBorder(null);
		lblYoutube.setBounds(10, 284, 48, 48);
		getContentPane().add(lblYoutube);
		
		lblTextoInsta = new JLabel("yFelpsz");
		lblTextoInsta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTextoInsta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://www.youtube.com/@yfelpsz2302");
			}
		});
		lblTextoInsta.setForeground(SystemColor.textHighlight);
		lblTextoInsta.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 17));
		lblTextoInsta.setBounds(69, 301, 91, 20);
		getContentPane().add(lblTextoInsta);
		
		JLabel lblNewLabel_1_2 = new JLabel("Versão 5.0");
		lblNewLabel_1_2.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		lblNewLabel_1_2.setBounds(10, 94, 104, 14);
		getContentPane().add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Repositório do projeto:");
		lblNewLabel_1_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		lblNewLabel_1_1_1.setBounds(10, 140, 214, 14);
		getContentPane().add(lblNewLabel_1_1_1);
		
		lblRepositorio = new JLabel("Clique aqui!");
		lblRepositorio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				link("https://github.com/felipea1/Academia-SistemaOS");
			}
		});
		lblRepositorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRepositorio.setForeground(SystemColor.textHighlight);
		lblRepositorio.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 17));
		lblRepositorio.setBounds(10, 165, 107, 20);
		getContentPane().add(lblRepositorio);
	} //Fim do construtor
	
	private void link(String site) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(site);
			desktop.browse(uri);			
		} catch (Exception e) {
			System.out.println();
		}
	}
}//Fim do código