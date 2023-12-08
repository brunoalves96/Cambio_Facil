package com.example.cmbiofcil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import kotlinx.coroutines.selects.select

class MainActivity : AppCompatActivity() {
    private lateinit var spinnerCoin1: Spinner //Criação de uma variával do tipo Spinner
    private lateinit var valorInserido: EditText //Criação de uma variával do tipo EditText
    private lateinit var spinnerCoin2: Spinner //Criação de uma segunda variával do tipo Spinner
    private lateinit var buttonConvert: Button //Criação de uma variával do tipo Button
    private lateinit var resultado: TextView //Criação de uma variával do tipo TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            spinnerCoin1.setSelection(0,true)//Para que o primeiro Spinner inicie com o primeiro item da lista selecionado.
            spinnerCoin2.adapter = adapter // Aplica o adaptador no segundo spinner.
            spinnerCoin2.setSelection(1,true) // Para que a segundo Spinner inicie com o segundo item da lista selecionado.
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

    private fun converterMoeda() {
        val selecao1 = spinnerCoin1.selectedItemPosition  //Variável para armazenar o número de posição (position) do item selecionado pelo usuário no 1º Spinner.
        val selecao2 = spinnerCoin2.selectedItemPosition  //Variável para armazenar o número de posição (position) do item selecionado pelo usuário no 2º Spinner.

        resultado.text = "Posição 1: $selecao1 e Posição 2: $selecao2"
    }

}