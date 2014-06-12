package com.tackmobile.test.accountmanager;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.util.UUID;

public class TestAccountManager extends Service {

  private TestAuthenticatorImpl mAuthenticator;

  @Override
  public IBinder onBind(Intent intent) {
    return getAccountAuthenticator().getIBinder();
  }

  private AbstractAccountAuthenticator getAccountAuthenticator() {
    if (mAuthenticator == null) {
      mAuthenticator = new TestAuthenticatorImpl(this);
    }
    return mAuthenticator;
  }

  private static class TestAuthenticatorImpl extends AbstractAccountAuthenticator {

    private final Context mContext;

    public TestAuthenticatorImpl(Context context) {
      super(context);
      mContext = context;
    }

    @Override public Bundle editProperties(AccountAuthenticatorResponse response,
                                           String accountType) {
      return null;
    }

    @Override public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
                                       String authTokenType, String[] requiredFeatures,
                                       Bundle options) throws NetworkErrorException {
      final Intent intent = new Intent(mContext, TestAddAccountActivity.class);
      intent.setAction("com.openclass.authenticator.SIGNIN");
      intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
      final Bundle bundle = new Bundle();
      bundle.putParcelable(AccountManager.KEY_INTENT, intent);
      return bundle;
    }

    @Override public Bundle confirmCredentials(AccountAuthenticatorResponse response,
                                               Account account,
                                               Bundle options) throws NetworkErrorException {
      return null;
    }

    @Override public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
                                         String authTokenType,
                                         Bundle options) throws NetworkErrorException {
      String fakeToken = UUID.randomUUID().toString();

      return makeResultBundle(account.name, account.type, fakeToken);
    }

    @Override public String getAuthTokenLabel(String authTokenType) {
      return null;
    }

    @Override public Bundle updateCredentials(AccountAuthenticatorResponse response,
                                              Account account,
                                              String authTokenType,
                                              Bundle options) throws NetworkErrorException {
      return null;
    }

    @Override public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account,
                                        String[] features) throws NetworkErrorException {
      return null;
    }


    private static Bundle makeResultBundle(String accountName, String accountType, String authToken) {
      Bundle b = new Bundle();
      b.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
      b.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
      b.putString(AccountManager.KEY_AUTHTOKEN, authToken);
      return b;
    }
  }
}
