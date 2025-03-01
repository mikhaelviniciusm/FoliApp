package com.mikhael.foliapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    // Declaração das variáveis
    private EditText etOrcamento, etTransporte, etHospedagem, etAlimentacao, etIngressos, etExtras;
    private Button btnCalcular, btnLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Deixar a barra de notificação e a barra de navegação transparentes
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Alterar a cor dos ícones da barra de status para preto
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // Inicialização dos componentes
        etOrcamento = findViewById(R.id.etOrcamento);
        etTransporte = findViewById(R.id.etTransporte);
        etHospedagem = findViewById(R.id.etHospedagem);
        etAlimentacao = findViewById(R.id.etAlimentacao);
        etIngressos = findViewById(R.id.etIngressos);
        etExtras = findViewById(R.id.etExtras);
//        tvResultado = findViewById(R.id.tvResultado);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);

        // Configuração do listener para o botão Calcular
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularOrcamento();
            }
        });

        // Configuração do listener para o botão Limpar
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparTudo();
            }
        });
    }

    // Obtém o valor decimal de um campo de texto
    private double getApenasDecimal(EditText editText) {
        String text = editText.getText().toString();
        return text.isEmpty() ? 0 : Double.parseDouble(text);
    }

    // Calcula o orçamento total
    private void calcularOrcamento() {
        double orcamentoTotal = getApenasDecimal(etOrcamento);

        // Verifica se o campo de orçamento está zerado
        if (orcamentoTotal == 0) {
            new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                    .setTitle("Erro")
                    .setMessage("O campo de orçamento não pode ficar zerado.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            return;
        }

        double transporte = getApenasDecimal(etTransporte);
        double hospedagem = getApenasDecimal(etHospedagem);
        double alimentacao = getApenasDecimal(etAlimentacao);
        double ingressos = getApenasDecimal(etIngressos);
        double extras = getApenasDecimal(etExtras);

        double totalDespesas = transporte + hospedagem + alimentacao + ingressos + extras;
        double resultado = orcamentoTotal - totalDespesas;

        // Mensagem a ser exibida
        String mensagem;
        if (resultado == 0) {
            mensagem = "Você pode viajar, mas seu orçamento ficará zerado.";
        } else if (resultado > 0) {
            mensagem = "Você pode viajar! Ainda irá sobrar R$ " + resultado;
        } else {
            mensagem = "Seu orçamento é insuficiente. Faltam R$ " + Math.abs(resultado);
        }

        // Exibe o resultado em um AlertDialog
        new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setTitle("Resultado")
                .setMessage(mensagem)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Fecha o dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    // Limpa todos os campos de texto e o resultado
    private void limparTudo() {
        etOrcamento.setText("");
        etTransporte.setText("");
        etHospedagem.setText("");
        etAlimentacao.setText("");
        etIngressos.setText("");
        etExtras.setText("");
    }
}