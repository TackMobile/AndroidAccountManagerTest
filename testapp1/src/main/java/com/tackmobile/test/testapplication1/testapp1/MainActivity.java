package com.tackmobile.test.testapplication1.testapp1;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

  final private static String ACCOUNT_TYPE = "com.tackmobile.account";
  final private static String AUTH_TOKEN_TYPE = "com.tackmobile";

  AccountManager mAccountManager;
  TextView mAccountsText;
  String mSelectedAccount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mAccountManager = (AccountManager) this.getSystemService(this.ACCOUNT_SERVICE);
    mAccountsText = (TextView) this.findViewById(R.id.accounts_text);

    updateAccounts();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_signin) {

      final AccountManagerFuture<Bundle> response = mAccountManager.addAccount(ACCOUNT_TYPE, AUTH_TOKEN_TYPE, null, null, this, null, null);

      //TODO: cleanup thread if signin activity is canceled via back button
      new Thread() {
        public void run() {
          try {
            Bundle b = response.getResult();
            mSelectedAccount = b.getString(AccountManager.KEY_ACCOUNT_NAME);
            MainActivity.this.runOnUiThread(new Runnable() {
              @Override
              public void run() {
                updateAccounts();
              }
            });
          } catch (OperationCanceledException e) {
            e.printStackTrace();
          } catch (AuthenticatorException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }.start();
    }
    return super.onOptionsItemSelected(item);
  }

  private void updateAccounts(){
    final Account[] accounts = mAccountManager.getAccountsByType(ACCOUNT_TYPE);

    String accountsText = "";
    for(Account account : accounts) {
      accountsText += account.name + " ";
      if(account.name.equals(mSelectedAccount)) {
        accountsText += "(x)";
      }
      accountsText += ",";
    }

    mAccountsText.setText(accountsText);
  }
}
