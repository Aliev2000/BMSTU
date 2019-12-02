package main

import "fmt"

type state struct {
        depth, i int
	used bool
	parent, pi *state
	stateAt []*state
}

func Find(v *state) *state {
	if v.parent == v {
		return v
	}
	v.parent = Find(v.parent)
	return v.parent

}

func Union(u *state, v *state) {
	root1 := Find(u)
	root2 := Find(v)
	if root1.depth < root2.depth {
		root1.parent = root2
	} else {
		root2.parent = root1
		if root1.depth == root2.depth && root1 != root2 {
			root1.depth++
		}
	}
	return
}

func split1(n , m int, stat []state, F [][]string) int {
	q := n
	for i := 0; i < n; i++ {
		stat[i].parent = &stat[i]
		stat[i].depth = 0
	}
	for q1 := 0; q1 < n; q1++ {
		for q2 := q1 + 1; q2 < n; q2++ {
			if Find(&stat[q1]) != Find(&stat[q2]) {
				eq := true
				for x := 0; x < m; x++ {
					if F[q1][x] != F[q2][x] {
						eq = false
						break
					}
				}
				if eq {
					Union(&stat[q1], &stat[q2])
					q--
				}
			}
		}
	}
	for q3 := 0; q3 < n; q3++ {
		stat[q3].pi = Find(&stat[q3])
	}
	return q
}

func split(n int, m int, stat []state) int {
	q := n
	for i := 0; i < n; i++ {
		stat[i].parent = &stat[i]
		stat[i].depth = 0
	}
	for q1 := 0; q1 < n; q1++ {
		for q2 := q1 + 1; q2 < n; q2++ {
			if stat[q1].pi == stat[q2].pi && Find(&stat[q1]) != Find(&stat[q2]) {
				eq := true
				for x := 0; x < m; x++ {
					w1 := stat[q1].stateAt[x]
					w2 := stat[q2].stateAt[x]
					if w1.pi != w2.pi {
						eq = false
						break
					}
				}
				if eq {
					Union(&stat[q1], &stat[q2])
					q--
				}
			}
		}
	}
	for q3 := 0; q3 < n; q3++ {
		stat[q3].pi = Find(&stat[q3])
	}
	return q
}

func AufenkampHohn(q0, n, m int, D [][]int, stat []state, F [][]string, iter *int) {
	h := split1(n , m, stat, F)
	for h1 := split(n, m, stat); h != h1; h1 = split(n, m, stat) {
		h = h1
	}
	DFS(q0, D, stat[q0].pi ,iter, m, stat)
}

func DFS(q0 int, D [][]int, s *state, iter *int, m int, stat []state) {
	s.i = *iter
	*iter++
	s.used = true
	for i := 0; i < m; i++ {
		qq := D[q0][i]
		if !s.stateAt[i].pi.used {
			DFS(qq, D, s.stateAt[i].pi, iter, m, stat)
		}
	}
}

func main() {
	var n1, m1, q01, i, j int
	fmt.Scan(&n1, &m1, &q01)
	D1 := make([][]int, n1)
	stat1 := make([]state, n1)
	for i = 0; i < n1; i++ {
		D1[i] = make([]int, m1)
		stat1[i].stateAt = make([]*state, m1)
		for j = 0; j < m1; j++ {
			fmt.Scan(&D1[i][j])
			stat1[i].stateAt[j] = &stat1[D1[i][j]]
		}
	}
	F1 := make([][]string, n1)
	for i = 0; i < n1; i++ {
		F1[i] = make([]string, m1)
		for j = 0; j < m1; j++ {
			fmt.Scan(&F1[i][j])
		}
	}

	var n2, m2, q02 int
	fmt.Scan(&n2, &m2, &q02)
	D2 := make([][]int, n2)
	stat2 := make([]state, n2)
	for i = 0; i < n2; i++ {
		D2[i] = make([]int, m2)
		stat2[i].stateAt = make([]*state, m2)
		for j = 0; j < m2; j++ {
			fmt.Scan(&D2[i][j])
			stat2[i].stateAt[j] = &stat2[D2[i][j]]
		}
	}
	F2 := make([][]string, n2)
	for i = 0; i < n2; i++ {
		F2[i] = make([]string, m2)
		for j = 0; j < m2; j++ {
			fmt.Scan(&F2[i][j])
		}
	}
	iter1 := 0
	iter2 := 0
	AufenkampHohn(q01, n1, m1, D1, stat1, F1, &iter1)
	AufenkampHohn(q02, n2, m2, D2, stat2, F2, &iter2)
	D12 := make([]string, iter1 * m1)
	iter11 := 0
	for i := 0; i < n1; i++ {
		if stat1[i].used {
			for j := 0; j < m1; j++ {
				D12[iter11] += fmt.Sprint(stat1[i].i, "", stat1[i].stateAt[j].pi.i, string('a'+j), F1[i][j], "\n")
				iter11++
			}
		}
	}
	D22 := make([]string, iter2 * m2)
	iter22 := 0
	for i := 0; i < n2; i++ {
		if stat2[i].used {
			for j := 0; j < m2; j++ {
				D22[iter22] += fmt.Sprint(stat2[i].i, "", stat2[i].stateAt[j].pi.i, string('a'+j), F2[i][j], "\n")
				iter22++
			}
		}
	}
	if iter1 == iter2 && m1 == m2{
		for i = 0; i < iter1 * m1; i++ {
			repeat := 0
			for j = 0; j < iter2 * m2; j++ {
				if D12[i] == D22[j] {
					repeat++
				}
			}
			if repeat != 1 {
				fmt.Print("NOT EQUAL")
				return
			}
		}
		fmt.Print("EQUAL")
	} else {
		fmt.Print("NOT EQUAL")
	}
}
