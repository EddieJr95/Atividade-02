import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserInterface {
    public JPanel painelPrincipal;

    private JLabel nomeLabel;
    private JTextField nome;
    private JLabel nomeEmBranco;

    private JLabel cpfLabel;
    private JTextField cpf;
    private JLabel validCPFLabel;

    private JLabel sexoLabel;
    private JRadioButton bSexoMasculino;
    private JRadioButton bSexoFeminino;

    private JLabel dataNascimentoLabel;
    private JFormattedTextField dataNascimento;

    private JLabel estadoCivilLabel;
    private JComboBox<String> estadoCivilBox;

    private JLabel profissaoLabel;
    private JTextField profissao;

    private JButton bSubmit;

    private JLabel nomeSubmit;
    private JLabel idadeSubmit;
    private JLabel sexoSubmit;
    private JLabel estadoCivilSubmit;
    private JLabel profissaoSubmit;
    private JLabel msgVagas;
    private JLabel validacaoData;
    private JLabel nomeSubmitLabel;
    private JLabel idadeSubmitLabel;
    private JLabel sexoSubmitLabel;
    private JLabel estadoSubmitLabel;
    private JLabel profissaoSubmitLabel;
    private JLabel cadastroNome;

    private String nomeText;
    private String cpfText;
    private String sexoText;
    private String estadoCivilText;

    private boolean nomeValido = false;
    private boolean cpfValido = false;
    private boolean dataValida = false;

    public UserInterface(){

        bSubmit.setEnabled(false);

        final String[] estadoCivil = {"","Solteiro(a)", "Casado(a)"};
        estadoCivilBox.setModel(new DefaultComboBoxModel<>(estadoCivil));

        try {
            MaskFormatter dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
            dataNascimento.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
        } catch (Exception e) {
            e.printStackTrace();
        }

        nome.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarNome();
                verificarCampos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarNome();
                verificarCampos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarNome();
                verificarCampos();
            }

            private void verificarNome() {
                nomeText = nome.getText();

                if(nomeText.isEmpty()){
                    nomeEmBranco.setVisible(true);
                    nomeEmBranco.setText("Nome não pode estar em branco");
                    nomeValido = false;
                } else {
                    nomeEmBranco.setVisible(false);
                    nomeValido = true;}
            }
        });

        cpf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarCPF();
                verificarCampos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarCPF();
                verificarCampos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarCPF();
                verificarCampos();
            }

            private void verificarCPF() {
                cpfText = cpf.getText();

                if(isValidCPF(cpfText)){
                    validCPFLabel.setVisible(true);
                    validCPFLabel.setText("CPF é válido");
                    cpfValido = true;
                } else {
                    validCPFLabel.setVisible(true);
                    validCPFLabel.setText("CPF é inválido");
                    cpfValido = false;
                }
            }
        });

        bSexoMasculino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sexoText = bSexoMasculino.getText();
            }
        });

        bSexoFeminino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sexoText = bSexoFeminino.getText();
            }
        });

        estadoCivilBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estadoCivilText = (String) estadoCivilBox.getSelectedItem();
            }
        });

        dataNascimento.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarData();
                verificarCampos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarData();
                verificarCampos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                verificarData();
                verificarCampos();
            }

            private void verificarData() {
                String dataText = dataNascimento.getText();
                if (dataText.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    String[] partes = dataText.split("/");
                    int dia = Integer.parseInt(partes[0]);
                    int mes = Integer.parseInt(partes[1]);
                    int ano = Integer.parseInt(partes[2]);

                    if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || ano < 1900 || ano > LocalDate.now().getYear()) {
                        validacaoData.setVisible(true);
                        validacaoData.setText("Data inválida");
                        dataValida = false;
                    } else{
                        validacaoData.setVisible(false);
                        dataValida = true;
                    }
                }
            }
        });

        bSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                validCPFLabel.setVisible(false);
                msgVagas.setVisible(false);

                nomeSubmitLabel.setVisible(true);
                idadeSubmitLabel.setVisible(true);
                sexoSubmitLabel.setVisible(true);
                estadoSubmitLabel.setVisible(true);
                profissaoSubmitLabel.setVisible(true);

                nomeSubmit.setVisible(true);
                idadeSubmit.setVisible(true);
                sexoSubmit.setVisible(true);
                estadoCivilSubmit.setVisible(true);
                profissaoSubmit.setVisible(true);

                nomeSubmit.setText(nomeText);

                int idade = calcularIdade(dataNascimento.getText());

                idadeSubmit.setText(String.valueOf(idade));

                sexoSubmit.setText(sexoText);
                estadoCivilSubmit.setText(estadoCivilText);

                if(profissao.getText().isEmpty()){
                    profissaoSubmit.setText("Desempregado(a)");
                } else if(profissao.getText().equalsIgnoreCase("Engenheiro") || profissao.getText().equalsIgnoreCase("Analista de Sistemas")){
                    msgVagas.setVisible(true);
                    profissaoSubmit.setText(profissao.getText());
                }
                else{ profissaoSubmit.setText(profissao.getText());}

                nome.setText("");
                cpf.setText("");
                dataNascimento.setText("");
                profissao.setText("");
                estadoCivilBox.setSelectedIndex(0);
                bSexoMasculino.setSelected(false);
                bSexoFeminino.setSelected(false);
                validacaoData.setVisible(false);
                nomeEmBranco.setVisible(false);
                validCPFLabel.setVisible(false);

                nomeText = "";
                cpfText = "";
                sexoText = "";
                estadoCivilText = "";

                bSubmit.setEnabled(false);
            }
        });
    }

    private void verificarCampos() {
        bSubmit.setEnabled(nomeValido && cpfValido && dataValida);
    }

    public static boolean isValidCPF(String cpf) {

        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador >= 10) {
            primeiroDigitoVerificador = 0;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador >= 10) {
            segundoDigitoVerificador = 0;
        }

        return cpf.charAt(9) - '0' == primeiroDigitoVerificador &&
                cpf.charAt(10) - '0' == segundoDigitoVerificador;
    }

    public static int calcularIdade(String dataNascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate dataNasc = LocalDate.parse(dataNascimento, formatter);
            LocalDate dataAtual = LocalDate.now();

            Period periodo = Period.between(dataNasc, dataAtual);

            return periodo.getYears();

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return -1;
        }
    }


}
