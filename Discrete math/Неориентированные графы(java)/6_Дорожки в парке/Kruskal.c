#include <stdio.h>
#include <stdlib.h>
#include <math.h>

struct Vertex {
        int x, y, depth;
	struct Vertex *parent;
} **vertex;

struct Edge {
	int u, v, len;
} *edge;

struct Vertex* Find(struct Vertex* v) {
	if(v->parent == v) return v;
	else return v->parent = Find(v->parent);
}

void Union(struct Vertex *u, struct Vertex *v) {
	struct Vertex *rootOfU = Find(u);
	struct Vertex *rootOfV = Find(v);
	if(rootOfU->depth < rootOfV->depth) rootOfU->parent = rootOfV;
	else rootOfV->parent = rootOfU;
}

int compare(const void *u, const void *v) {
	const struct Edge *a = u;
	const struct Edge *b = v;
	if (a->len > b->len) return 1;
	else if(a->len == b->len) return 0;
	return -1;
}

void MST_Kruskal(int M, int N) {
	qsort(edge, M, sizeof(struct Edge), compare);
	int i = -1, counter = 0;
	double mst = 0;
	while(++i < M && counter < N - 1)
		if(Find(vertex[edge[i].u]) != Find(vertex[edge[i].v])) {
			mst += sqrt(edge[i].len);
			Union(vertex[edge[i].u], vertex[edge[i].v]);
			counter++;
		}
	printf("%.2f", mst);
}

int main(int argc, char **argv) {
	int N, M, x, y, helper = 0;
	scanf("%d", &N);
	M = N * (N - 1) / 2;
	vertex = malloc(N * sizeof(struct Vertex*));
	for(int i = 0; i < N; i++) {
		scanf("%d%d", &x, &y);
		vertex[i] = malloc(sizeof(struct Vertex));
		vertex[i]->x = x;
		vertex[i]->y = y;
		vertex[i]->depth = 0;
		vertex[i]->parent = vertex[i];
	}
	edge = malloc(M * sizeof(struct Edge));
	for(int i = 0; i < N; i++) {
		for(int j = i + 1; j < N; j++) {
			edge[helper].u = i;
			edge[helper].v = j;
			edge[helper++].len = (vertex[i]->x - vertex[j]->x) * (vertex[i]->x - vertex[j]->x) + (vertex[i]->y - vertex[j]->y) * (vertex[i]->y - vertex[j]->y);
		}
	}
	MST_Kruskal(M, N);
	for(int i = 0; i < N; i++)
		free(vertex[i]);
}
