package com.example.cmbiofcil

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    private lateinit var spinnerCoin1: Spinner //Criação de uma variával do tipo Spinner
    private lateinit var valorInserido: EditText //Criação de uma variával do tipo EditText
    private lateinit var spinnerCoin2: Spinner //Criação de uma segunda variával do tipo Spinner
    private lateinit var buttonConvert: Button //Criação de uma variával do tipo Button
    private lateinit var resultado: TextView//Criação de uma variával do tipo TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#7ED957")

        spinnerCoin1 = findViewById(R.id.spinnerListCoin1) // Inicia a variável buscando a view Spinner pela Id atribuida no arquivo xml
        valorInserido = findViewById(R.id.editTextValueCoin) // Inicia a variável buscando a view EditText pela Id atribuida no arquivo xml
        spinnerCoin2 = findViewById(R.id.spinnerListCoin2) // Inicia a variável buscando a  segunda view Spinner pela Id atribuida no arquivo xml
        buttonConvert = findViewById(R.id.buttonConvert) // Inicia a variável buscando a view Button pela Id atribuida no arquivo xml
        resultado = findViewById(R.id.textViewResultado) // Inicia a variável buscando a view TextView pela Id atribuida no arquivo xml

        ArrayAdapter.createFromResource(    // Cria um ArrayAdapter, utilizando o array no "strins.xml" e define o layout do splinter
            this,
            R.array.list_coins,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter -> // Especifica o tipo de layout a ser utilizado nos spinners
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCoin1.adapter = adapter // Aplica o adaptador no primeiro spinner.
            spinnerCoin1.setSelection(1,true)//Para que o primeiro Spinner inicie com o segundo item da lista selecionado.
            spinnerCoin2.adapter = adapter // Aplica o adaptador no segundo spinner.
            spinnerCoin2.setSelection(0,true) // Para que a segundo Spinner inicie com o primeiro item da lista selecionado.
        }


        spinnerCoin1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{  //Essa interface permite definir um evento para o item selecionado pelo o usuário no primeiro spinner.
            override fun onItemSelected(  //Método que executa uma ação quando o usuário clicar no item e
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {  //Método que executa uma ação quando o usuário não clicar em nenhum item.

            }

        }

        spinnerCoin2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{  //Essa interface permite definir um evento para o item selecionado pelo o usuário no segundo spinner.
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        buttonConvert.setOnClickListener{ converterMoeda() } //Criado o método que vai executar a função quando o usuário clicar no botão de Converter

    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    private fun converterMoeda() {
        val selecao1 =
            spinnerCoin1.selectedItemPosition.toString()  //Variável para armazenar o número de posição (position) do item selecionado pelo usuário no 1º Spinner e converte em uma STRING.
        val selecao2 =
            spinnerCoin2.selectedItemPosition.toString()  //Variável para armazenar o número de posição (position) do item selecionado pelo usuário no 2º Spinner e converte em uma STRING.

        val moeda1 = when(selecao1){     //-> A variável utiliza uma condicional que recebe o número da posição da variável "selecao1" e retorna a
            "0" -> "BRL"                 // sigla da moeda correspondente.
            "1" -> "USD"
            "2" -> "EUR"
            else -> {"JPY"}
        }

        val moeda2 = when(selecao2){     //-> A variável utiliza uma condicional que recebe o número da posição da variável "selecao2" e retorna a
            "0" -> "BRL"                 // sigla da moeda correspondente.
            "1" -> "USD"
            "2" -> "EUR"
            else -> {"JPY"}
        }

        val valorDigitado = valorInserido.text.toString()   // A variável recebe o valor inserido pelo usuário e converte em uma STRING.

        if(valorDigitado.isEmpty() || moeda1 == moeda2){   // A condicional verifica se o usuário não digitou um valor ou se as moedas selecionadas são iguais.
            return                 // Para a execução do código caso a condicional seja verdadeira.
        }

        Thread{  //Esse processo executa o bloco de código abaixo de forma paralela ao bloco de código executado na MainActivity

            val link = URL("https://economia.awesomeapi.com.br/last/$moeda1-$moeda2") //A variável utiliza o objeto URl dinâmica que é modificada com os valores das duas variáveis, ou seja, as moedas escolhidas pelo usuário.
            val conexao = link.openConnection() as HttpsURLConnection // A variável inicia uma conxão de rede e transforma em uma conexão https

                try {   //Executa um bloco de código caso não ocorra erro na conexão de rede.
                    val dados = conexao.inputStream.bufferedReader().readText() //A variável irá acessar os dados da API(em formato bit) e converter esses dados em texto
                    val objeto = JSONObject(dados) //A variável executa o objeto JSONObject para receber os dados da API que são formatados em JSON

                    runOnUiThread{  //Esse recurso leva os dados obtidos para a MainActivity
                    val buscarObjeto = objeto.getJSONObject("${moeda1+moeda2}") //Acessa a chave principal na API que tem o nome das duas moedas escolhidas

                        val cotacao = buscarObjeto.getDouble("bid")  //Acessa dentro da chave principai, a chave que contem o valor da cotação das duas moedas
                        val infAtualizacao = buscarObjeto.getString("create_date") //Acessa dentro da chave principal a data e o horário da ultia atulização da cotação

                        val calculo = (valorDigitado.toDouble())*cotacao  // Realiza o calculo da conversão.


                        resultado.text = "RESULTADO DA CONVERSÃO\n\n"+   //Mostra o resultado ao usuário na TextView, com um texto dinâmico.
                                "Conversão de $moeda1 para $moeda2\n\n"+
                                "Resultado: ${"%.2f".format(calculo)}\n\n" +
                                "Taxa da Cotação: ${"%.2f".format(cotacao)}\n\n" +
                                "Última Atualização: $infAtualizacao"

                    }

                }
                finally {    //Esse bloco é executado após a execução bloco TRY ou se ocorrer erro na conexão.
                    conexao.disconnect()  //Fecha a conexão de rede
                }

        }.start() // Inicia o processo "Thread"

    }

}