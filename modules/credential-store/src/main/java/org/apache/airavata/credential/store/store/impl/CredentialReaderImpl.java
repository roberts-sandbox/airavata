package org.apache.airavata.credential.store.store.impl;

import org.apache.airavata.common.utils.DBUtil;
import org.apache.airavata.credential.store.credential.CommunityUser;
import org.apache.airavata.credential.store.credential.Credential;
import org.apache.airavata.credential.store.credential.impl.certificate.CertificateAuditInfo;
import org.apache.airavata.credential.store.credential.impl.certificate.CertificateCredential;
import org.apache.airavata.credential.store.store.CredentialReader;
import org.apache.airavata.credential.store.store.impl.db.CredentialsDAO;
import org.apache.airavata.credential.store.store.CredentialStoreException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Credential store API implementation.
 */
public class CredentialReaderImpl implements CredentialReader, Serializable {

    private CredentialsDAO credentialsDAO;

    private DBUtil dbUtil;

    public CredentialReaderImpl(DBUtil dbUtil) {

        this.credentialsDAO = new CredentialsDAO();

        this.dbUtil = dbUtil;
    }

    private Connection getConnection() throws CredentialStoreException {
        try {
            return this.dbUtil.getConnection();
        } catch (SQLException e) {
            throw new CredentialStoreException("Unable to retrieve database connection.", e);
        }
    }

    public String getPortalUser(String gatewayName, String tokenId) throws CredentialStoreException {

        Connection connection = getConnection();

        Credential credential;

        try {
             credential
                    = this.credentialsDAO.getCredential(gatewayName, tokenId, connection);

        } finally {
            DBUtil.cleanup(connection);
        }

        return credential.getPortalUserName();
    }

    public CertificateAuditInfo getAuditInfo(String gatewayName, String tokenId)
            throws CredentialStoreException {

        Connection connection = getConnection();

        CertificateAuditInfo certificateAuditInfo;

        try {

            CertificateCredential certificateCredential
                    = (CertificateCredential)this.credentialsDAO.getCredential(gatewayName, tokenId, connection);

            certificateAuditInfo = new CertificateAuditInfo();

            CommunityUser retrievedUser = certificateCredential.getCommunityUser();
            certificateAuditInfo.setCommunityUserName(retrievedUser.getUserName());
            certificateAuditInfo.setCredentialLifeTime(certificateCredential.getLifeTime());
            certificateAuditInfo.setCredentialsRequestedTime(certificateCredential.getCertificateRequestedTime());
            certificateAuditInfo.setGatewayName(gatewayName);
            certificateAuditInfo.setNotAfter(certificateCredential.getNotAfter());
            certificateAuditInfo.setNotBefore(certificateCredential.getNotBefore());
            certificateAuditInfo.setPortalUserName(certificateCredential.getPortalUserName());

        } finally {
            DBUtil.cleanup(connection);
        }

        return certificateAuditInfo;
    }

    public void updateCommunityUserEmail(String gatewayName, String communityUser, String email)
            throws CredentialStoreException {
        //TODO
    }

    public void removeCredentials(String gatewayName, String tokenId) throws CredentialStoreException {

        Connection connection = getConnection();

        try {
            credentialsDAO.deleteCredentials(gatewayName, tokenId, connection);
        } finally {
            DBUtil.cleanup(connection);
        }

    }



}