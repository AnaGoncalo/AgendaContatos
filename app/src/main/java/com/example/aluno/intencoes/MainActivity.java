package com.example.aluno.intencoes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    private final int ITEM_NOVOCONTATO = Menu.FIRST;
    private final int ITEM_SOBRE = Menu.FIRST+1;
    private TableLayout tableLayout;
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TableRow row = (TableRow) v.getParent();
            EditText fone = (EditText) row.findViewById(R.id.showFone);
            String s = "tell:" + fone.getText().toString();
            Uri uri = Uri.parse(s);
            Intent callFone = new Intent(Intent.ACTION_CALL, uri);
            try{
                startActivity(callFone);
            }
            catch (SecurityException sexep){

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(Menu.NONE, ITEM_NOVOCONTATO, Menu.NONE, R.string.action_novo);
        menu.add(Menu.NONE, ITEM_SOBRE, Menu.NONE, R.string.action_sobre);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case ITEM_NOVOCONTATO:
                Intent novo_contato = new Intent(this, NovoContatoActivity.class);
                startActivityForResult(novo_contato, 0);
                break;
            case ITEM_SOBRE:
                Intent sobre = new Intent(this, SobreActivity.class);
                startActivityForResult(sobre, 0);
                break;
            case R.id.action_fechar:
                finish();
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                String nome = data.getCharSequenceExtra("nome").toString();
                String fone = data.getCharSequenceExtra("fone").toString();
                inserirContato(nome, fone);
            }
        }
    }

    private void inserirContato(String nome, String fone) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.tr_novo_contato, null);

        EditText editnome = (EditText) row.findViewById(R.id.showNome);
        EditText editfone = (EditText) row.findViewById(R.id.showFone);
        Button btnLigar = (Button) row.findViewById(R.id.btnLigar);

        editnome.setText(nome);
        editfone.setText(fone);
        btnLigar.setOnClickListener(buttonListener);

        tableLayout.addView(row);
    }
}
