package br.com.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt0 = findViewById(R.id.bt0);
        Button bt1 = findViewById(R.id.bt1);
        Button bt2 = findViewById(R.id.bt2);
        Button bt3 = findViewById(R.id.bt3);
        Button bt4 = findViewById(R.id.bt4);
        Button bt5 = findViewById(R.id.bt5);
        Button bt6 = findViewById(R.id.bt6);
        Button bt7 = findViewById(R.id.bt7);
        Button bt8 = findViewById(R.id.bt8);
        Button bt9 = findViewById(R.id.bt9);

        Button btLimpar = findViewById(R.id.btLimpar);
        Button btResultado = findViewById(R.id.btResultado);
        Button btPonto = findViewById(R.id.btPonto);
        Button btDivisao = findViewById(R.id.btDivisao);
        Button btSoma = findViewById(R.id.btSoma);
        Button btSubtracao = findViewById(R.id.btSubtracao);
        Button btMultiplicacao = findViewById(R.id.btMultiplicacao);
        Button btRemoverUltimo = findViewById(R.id.btRemoverUltimo);

        TextView saida = findViewById(R.id.txtSaida);

        bt0.setOnClickListener(b -> saida.setText(adicionarASaida(bt0.getText().toString(), saida)));
        bt1.setOnClickListener(b -> saida.setText(adicionarASaida(bt1.getText().toString(), saida)));
        bt2.setOnClickListener(b -> saida.setText(adicionarASaida(bt2.getText().toString(), saida)));
        bt3.setOnClickListener(b -> saida.setText(adicionarASaida(bt3.getText().toString(), saida)));
        bt4.setOnClickListener(b -> saida.setText(adicionarASaida(bt4.getText().toString(), saida)));
        bt5.setOnClickListener(b -> saida.setText(adicionarASaida(bt5.getText().toString(), saida)));
        bt6.setOnClickListener(b -> saida.setText(adicionarASaida(bt6.getText().toString(), saida)));
        bt7.setOnClickListener(b -> saida.setText(adicionarASaida(bt7.getText().toString(), saida)));
        bt8.setOnClickListener(b -> saida.setText(adicionarASaida(bt8.getText().toString(), saida)));
        bt9.setOnClickListener(b -> saida.setText(adicionarASaida(bt9.getText().toString(), saida)));
        btPonto.setOnClickListener(b -> saida.setText(adicionarASaida(btPonto.getText().toString(), saida)));

        btDivisao.setOnClickListener(b -> {
            if (contemOperacao(saida)) {
                saida.setText(calcularResultado(saida));
            }
            saida.setText(adicionarASaida(btDivisao.getText().toString(), saida));
        });

        btMultiplicacao.setOnClickListener(b -> {
            if (contemOperacao(saida)) {
                saida.setText(calcularResultado(saida));
            }
            saida.setText(adicionarASaida(btMultiplicacao.getText().toString(), saida));
        });

        btSubtracao.setOnClickListener(b -> {
            if (contemOperacao(saida)) {
                saida.setText(calcularResultado(saida));
            }
            saida.setText(adicionarASaida(btSubtracao.getText().toString(), saida));
        });

        btSoma.setOnClickListener(b -> {
            if (contemOperacao(saida)) {
                saida.setText(calcularResultado(saida));
            }
            saida.setText(adicionarASaida(btSoma.getText().toString(), saida));
        });

        btResultado.setOnClickListener(b -> {
            if (contemOperacao(saida)) {
                saida.setText(calcularResultado(saida));
            }else{
                String mensagem = "OPERAÇÃO INVÁLIDA";
                saida.setText(mensagem);
            }
       });

        btLimpar.setOnClickListener(b -> limparSaida(saida));

        btRemoverUltimo.setOnClickListener(b -> {
            if(saida.getText().length() > 0){
                removerUltimoDigito(saida);
            }
        });

    }


    public boolean contemOperacao(TextView saida) {

        //SO DIGITOS
        String saidaSemSinais = saida.getText().toString().replaceAll("\\+", "")
        .replaceAll("-", "").replaceAll("\\*", "")
        .replaceAll("÷", "").replaceAll(" ", "");
        if(saidaSemSinais.isEmpty()){
            return false;
        }

        //COMEÇA COM DIGITO E NAO É NEGATIVO
        if (saida.getText().toString().startsWith("+") || saida.getText().toString().startsWith("*")
        || saida.getText().toString().startsWith("÷")){
            return false;
        }

        boolean numeroNegativo = saida.getText().toString().startsWith("-");


        int contadorDeSinais = 0;
         for (Character ch : saida.getText().toString().toCharArray()) {
            if (!Character.isDigit(ch) && !ch.toString().equals(".")) {
                contadorDeSinais++;
            }
        }

        if(contadorDeSinais == 1){
           if(numeroNegativo){
                return false;
            }
            return true;
        }else if(contadorDeSinais == 2 && numeroNegativo){
            return true;
        }
        return false;
    }


    public String calcularResultado(TextView saida) {
        String simbolo = "";
        List<String> numeros;

        for (Character ch : saida.getText().toString().toCharArray()) {

            if (!Character.isDigit(ch) && !ch.toString().equals(".")) {
                simbolo = String.valueOf(ch);
           }
        }

        switch (simbolo) {
            case "+":
                numeros = Arrays.asList(saida.getText().toString().split("\\+"));
                return somar(new BigDecimal(numeros.get(0)), new BigDecimal(numeros.get(1)));
            case "-":
                boolean numeroNegativo = saida.getText().toString().startsWith("-");
                if(numeroNegativo){
                    numeros = Arrays.asList(saida.getText().toString().substring(1).split("-"));
                    numeros.set(0, "-".concat(numeros.get(0)));
                }else{
                    numeros = Arrays.asList(saida.getText().toString().split("-"));
                }
               return subtrair(new BigDecimal(numeros.get(0)), new BigDecimal(numeros.get(1)));
            case "÷":
                numeros = Arrays.asList(saida.getText().toString().split("÷"));
                if(numeros.get(1).equals("0")){
                    String mensagem = "OPERAÇÃO INVÁLIDA";
                    return mensagem;
                }
                return dividir(new BigDecimal(numeros.get(0)), new BigDecimal(numeros.get(1)));
            case "*":
                numeros = Arrays.asList(saida.getText().toString().split("\\*"));
                return multiplicar(new BigDecimal(numeros.get(0)), new BigDecimal(numeros.get(1)));
        }
        return simbolo;
    }

    public void limparSaida(TextView saida) {
        saida.setText("");
    }

    public void removerUltimoDigito(TextView saida){
        String novaSaida = saida.getText().subSequence(0, saida.getText().length() - 1).toString();
        saida.setText(novaSaida);
    }

    public String adicionarASaida(String texto, TextView saida) {
        StringBuilder sb = new StringBuilder()
                .append(saida.getText())
                .append(texto);
        return sb.toString();
    }

    public String multiplicar(BigDecimal numero1, BigDecimal numero2) {
        BigDecimal resultado = numero1.multiply(numero2);
        return resultado.toString();
    }

    public String dividir(BigDecimal numero1, BigDecimal numero2) {
        BigDecimal resultado = numero1.divide(numero2);
        return resultado.toString();
    }

    public String subtrair(BigDecimal numero1, BigDecimal numero2) {
        BigDecimal resultado = numero1.subtract(numero2);
        return resultado.toString();
    }

    public String somar(BigDecimal numero1, BigDecimal numero2) {
        BigDecimal resultado = numero1.add(numero2);
        return resultado.toString();
    }
}
