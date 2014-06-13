package com.tackmobile.test.testapplication2.testapp2;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity {

  final private static String ACCOUNT_TYPE = "com.tackmobile.account";
  final private static String AUTH_TOKEN_TYPE = "com.tackmobile";

  private TextView mAccountsText;
  private String mSelectedAccount;
  private AccountManager mAccountManager;

  private AccountManagerCallback<Bundle> mAddAccountCallback = new AccountManagerCallback<Bundle>() {
    @Override public void run(AccountManagerFuture<Bundle> future) {
      try {
        mSelectedAccount =  future.getResult().getString(AccountManager.KEY_ACCOUNT_NAME);
        updateAccounts();

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
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mAccountManager = AccountManager.get(this);

    findViewById(R.id.add_account_button).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mAccountManager.addAccount(ACCOUNT_TYPE, AUTH_TOKEN_TYPE, null, null, MainActivity.this, mAddAccountCallback, null);
      }
    });

    findViewById(R.id.get_auth_token_button).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });

    findViewById(R.id.get_auth_token_by_features_button).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });

    findViewById(R.id.invalidate_button).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

      }
    });

    mAccountManager = (AccountManager) this.getSystemService(this.ACCOUNT_SERVICE);
    mAccountsText = (TextView) this.findViewById(R.id.accounts_text);
    updateAccounts();
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
