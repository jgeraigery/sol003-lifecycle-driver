# Installation Guide

## Helm Install of Driver

Prior to installing the driver, it may be necessary to:
 - configure the source for VNF Packages. See [Configuring VNF Package Location](ConfiguringVNFPackageLocation.md)
 - configure the Kafka host and create mandatory topics. See [Configuring Kafka](ConfiguringKafka.md)
 - configure a secret containing trusted client certificates. See [Configuring Certificates](ConfiguringCertificates.md)


Download the Helm chart for the required version of the VNFM Driver. Run the following command to install the Helm chart with the default values:

```bash
helm install sol003-lifecycle-driver sol003-lifecycle-driver-<version>.tgz
```

## Onboarding Driver into LM

Use lmctl for onboard the driver into LM. For full details on how to install or use lmctl, refer to its documentation.

Sol003 certificate which is in secret sol003-lifecycle-driver-tls need to be used while onboarding VNFM driver. Following command can be used to obtain sol003 certificate.

```bash
oc get secret sol003-lifecycle-driver-tls -o 'go-template={{index .data "tls.crt"}}' | base64 -d > sol003-lifecycle-tls.pem
```

The following command will onboard the VNFM Driver into into CP4NA environment called 'dev01':

```bash
lmctl resourcedriver add --type sol003 --url https://sol003-lifecycle-driver:8296 dev01 --certificate sol003-lifecycle-tls.pem
```

## Create route for sol003

A route need to be created to access VNFM driver externally. In this example we are using reencrypt route.

Create certificates for the route.

```bash
openssl req -newkey rsa:2048 -keyout route-tls.key -x509 -days 365 -out route-tls.crt -nodes
```
**NOTES**: Use Common Name as ```sol003-lifecycle-driver.apps.<cluster-name>.<domain-name>``` while creating certificates.

Get CA certificate from secret.

```bash
oc get secret sol003-lifecycle-driver-tls -o 'go-template={{index .data "ca.crt"}}' | base64 -d > sol003-ca.crt
```

Create route.

```bash
oc create route reencrypt --service=sol003-lifecycle-driver --cert=route-tls.crt --key=route-tls.key --dest-ca-cert=sol003-ca.crt --hostname=sol003-lifecycle-driver.apps.<cluster-name>.<domain-name>
```


**NOTES**:
- The above example assumes lmctl has been configured with an environment called 'dev01'. Replace this environment name accordingly
- If this configuration doesn't include the password for the environment, one will be prompted for
