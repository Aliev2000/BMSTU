package main

import "fmt"

type state struct {
	depth, i int
	used bool
	parent, pi *state
	stateAt []*state
} 

func find(v *state) *state {
	if v.parent == v {
		return v
	}
	v.parent = find(v.parent)
	return v.parent

}

func union(u *state, v *state) {
	root1 := find(u)
	root2 := find(v)
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
			if find(&stat[q1]) != find(&stat[q2]) {
				eq := true
				for x := 0; x < m; x++ {
					if F[q1][x] != F[q2][x] {
						eq = false
						break
					}
				}
				if eq {
					union(&stat[q1], &stat[q2])
					q--
				}
			}
		}
	}
	for q3 := 0; q3 < n; q3++ {
		stat[q3].pi = find(&stat[q3])
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
			if stat[q1].pi == stat[q2].pi && find(&stat[q1]) != find(&stat[q2]) {
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
					union(&stat[q1], &stat[q2])
					q--
				}
			}
		}
	}
	for q3 := 0; q3 < n; q3++ {
		stat[q3].pi = find(&stat[q3])
	}
	return q
}

func aufenkampHohn(q0, n, m int, D [][]int, stat []state, F [][]string, iter *int) {
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
	var n, m, q0, i, j int
	fmt.Scan(&n, &m, &q0)
	D := make([][]int, n)
	stat := make([]state, n)
	for i = 0; i < n; i++ {
		D[i] = make([]int, m)
		stat[i].stateAt = make([]*state, m)
		for j = 0; j < m; j++ {
			fmt.Scan(&D[i][j])
			stat[i].stateAt[j] = &stat[D[i][j]]
		}
	}
	F := make([][]string, n)
	for i = 0; i < n; i++ {
		F[i] = make([]string, m)
		for j = 0; j < m; j++ {
			fmt.Scan(&F[i][j])
		}
	}
	iter := 0
	aufenkampHohn(q0, n, m, D, stat, F, &iter)
	fmt.Printf("digraph {\n")
	fmt.Printf("\t rankdir = LR\n")
	fmt.Printf("\t dummy [label = \"\", shape = none]\n")
	for i = 0; i < iter; i++ {
		fmt.Printf("\t %d [shape = circle] \n", i)
	}
	fmt.Printf("\t dummy -> %d \n", 0)
	for i := 0; i < n; i++ {
		if stat[i].used {
			for j := 0; j < m; j++ {
				fmt.Printf("\t %d -> %d [label = \"%c(%s)\"]\n", stat[i].i, stat[i].stateAt[j].pi.i, 'a'+j, F[i][j])
			}
		}
	}
	fmt.Print("}")
}
