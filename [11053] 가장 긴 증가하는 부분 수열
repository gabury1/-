// Online C++ compiler to run C++ program online
#include <iostream>

using namespace std;

int dp[1001];
int arr[1001];

int max(int a, int b) {return a>b ? a:b;}

int main() {
    
    int n;
    cin >> n;
    for(int i=0; i<n; i++) cin >> arr[i];
    
    dp[0] = 1;
    for(int i=1; i<n; i++){
        int maxi = 0;
        for(int j=0; j<i; j++)
            if(arr[j] < arr[i]) maxi = max(maxi, dp[j]);
        dp[i] = maxi+1;
    }
    
    int ans = 0;
    for(int i=0; i<n; i++) ans = max(ans, dp[i]);
    
    cout << ans << endl;
    
    return 0;
}
