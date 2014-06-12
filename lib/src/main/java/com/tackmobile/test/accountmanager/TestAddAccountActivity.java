package com.tackmobile.test.accountmanager;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author Joshua Jamison
 */
public class TestAddAccountActivity extends AccountAuthenticatorActivity {

  private AccountManager mAccountManager;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.signin);

    mAccountManager = AccountManager.get(getBaseContext());

    findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        signin();
      }
    });

    findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        signup();
      }
    });
  }

  private void signin() {

    final String accountType = "com.tackmobile.account";
    final Account[] accounts = mAccountManager.getAccountsByType(accountType);
    final String accountName = "username" + (accounts.length - 1);
    final String accountPassword = "password" + (accounts.length - 1);
    final String authToken = "123456";

    final Account account = new Account(accountName, accountType);
    mAccountManager.addAccountExplicitly(account, accountPassword, null);
    mAccountManager.setAuthToken(account, accountType, authToken);

    final Bundle data = new Bundle();
    data.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
    data.putString(AccountManager.KEY_AUTHTOKEN, authToken);
    setAccountAuthenticatorResult(data);

    final Intent intent = new Intent();
    intent.putExtras(data);
    setResult(RESULT_OK, intent);

    finish();
  }

  private void signup() {

    final String accountType = "com.tackmobile.account";
    final Account[] accounts = mAccountManager.getAccountsByType(accountType);
    final String accountName = "username" + accounts.length;
    final String accountPassword = "password" + accounts.length;
    final String authToken = "123456";

    final Account account = new Account(accountName, accountType);
    mAccountManager.addAccountExplicitly(account, accountPassword, null);
    mAccountManager.setAuthToken(account, accountType, authToken);

    final Bundle data = new Bundle();
    data.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
    data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
    data.putString(AccountManager.KEY_AUTHTOKEN, authToken);
    setAccountAuthenticatorResult(data);

    final Intent intent = new Intent();
    intent.putExtras(data);
    setResult(RESULT_OK, intent);

    finish();
  }


}
