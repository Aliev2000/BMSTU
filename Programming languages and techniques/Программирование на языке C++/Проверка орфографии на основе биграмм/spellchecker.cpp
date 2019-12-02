#include <bits/stdc++.h>

using namespace std;

set<string> Make_Bigramm_Set(string s) {
    set<string> bigramm_set;
    if(s.length() <= 1) {
        bigramm_set.emplace(s);
        return bigramm_set;
    }
    for(int i = 0; i < s.size() - 1; i++)
        bigramm_set.emplace(string{s[i], s[i + 1]});
    return bigramm_set;
}

int main() {
    map<string, pair<set<string>, int>> dict;
    ifstream in("count_big.txt");
    while(in) {
        string s;
        int freq;
        in >> s >> freq;
        dict.emplace(s, make_pair(Make_Bigramm_Set(s), freq));
    }
    in.close();
    do {
        string s;
        double w = -0.1, w2;
        int freq = 0;
        cin >> s;
        set<string> U = Make_Bigramm_Set(s);
        for(auto it = dict.begin(); it != dict.end(); it++) {
            set<string> W;
            set_intersection(U.begin(), U.end(), it->second.first.begin(), it->second.first.end(),inserter(W, W.begin()));
            w2 = (double) W.size() / (U.size() + it->second.first.size() - W.size());
            if(w2 > w) {
                w = w2;
                freq = it->second.second;
                s = it->first;
            } else if(w2 == w && it->second.second > freq) {
                freq = it->second.second;
                s = it->first;
            } else if(w2 == w && it->second.second == freq && it->first < s)
                s = it->first;
        }
        if(cin)    cout << s << endl;
    } while(cin);
}
