#include <bits/stdc++.h>
#include "textstats.hpp"
using namespace std;

void get_tokens(const string &s, const unordered_set<char> &delimiters, vector<string> &tokens) {
    string temp = "";
    for(auto it = s.begin(); it != s.end(); it++) {
        if (delimiters.find(*it) == delimiters.end())      
            temp.push_back(tolower(*it));
        else if (!temp.empty()) {
            tokens.push_back(temp);
            temp = "";
        }
    }
    if(!temp.empty())
        tokens.push_back(temp);
}

void get_type_freq(const vector<string> &tokens,  map<string, int> &freqdi) {
    for(auto it = tokens.begin(); it != tokens.end(); it++)     freqdi[*it]++;
}

void get_types(const vector<string> &tokens,vector<string> &wtypes) {
    set<string> temp;
    for(auto it = tokens.begin(); it != tokens.end(); it++)     temp.insert(*it);
    for(auto it = temp.begin(); it != temp.end(); it++)     wtypes.push_back(*it);
}

void get_x_length_words(const vector<string> &wtypes, int x, vector<string> &words) {
    for(auto it = wtypes.begin(); it != wtypes.end(); it++)
        if ((*it).length() >= x) words.push_back(*it);
}

void get_x_freq_words(const map<string, int> &freqdi, int x, vector<string> &words) {
    for(auto it = freqdi.begin(); it != freqdi.end(); it++)
        if (it->second >= x)    words.push_back(it->first);
}

void get_words_by_length_dict(const vector<string> &wtypes, map<int, vector<string> > &lengthdi){
    for(auto it = wtypes.begin(); it != wtypes.end(); it++)
        lengthdi[(*it).length()].push_back(*it);
}
