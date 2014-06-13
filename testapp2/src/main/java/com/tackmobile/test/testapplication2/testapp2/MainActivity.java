package com.tackmobile.test.testapplication2.testapp2;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

  private static final String ACCOUNT_TYPE = "testAccountType";

  private static final String AUTH_TOKEN_TYPE = "testTokenType";

  private AccountManager mAccountManager;

  private AccountManagerCallback<Bundle> mAddAccountCallback = new AccountManagerCallback<Bundle>() {
    @Override public void run(AccountManagerFuture<Bundle> future) {

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
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {

      return true;
    }
    return super.onOptionsItemSelected(item);
  }


}
