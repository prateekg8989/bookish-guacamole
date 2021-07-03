import { CognitoUserPool }  from 'amazon-cognito-identity-js';
const poolData = {
    UserPoolId: 'us-east-2_ULwbZAKdb',
    ClientId: '3bhqdd5d65jcki32h68soq46td'
}

export default new CognitoUserPool(poolData);