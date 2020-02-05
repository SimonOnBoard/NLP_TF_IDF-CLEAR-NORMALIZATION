from sklearn.feature_extraction.text import TfidfVectorizer
from scipy.spatial import distance
text = []

with open('friends.txt', 'r') as f:
    strings = f.read().splitlines()
print(strings)

with open('myTextAfterNormalizationProcess.txt', 'r', encoding='utf-8') as infile:
            var = infile.read()
            text.append(var)
            infile.close()

for val in strings:
    try:
        with open(val + '.txt', 'r', encoding='utf-8') as infile:
            var = infile.read()
            text.append(var)
            infile.close()
    except FileNotFoundError:
        print('file not found' + val)

print(text.__len__())

vectorizer = TfidfVectorizer()
matrix = vectorizer.fit_transform(text)
words = vectorizer.get_feature_names()
print(len(words))
print(matrix.getrow(1).shape[1])

def cos(a, b):
  return 1 -  distance.cosine(a, b)


d1 = dict()
for i in range(len(text) - 1):
    d1['vk.com/id' + strings[i]] = cos(matrix.todense()[0], matrix.todense()[i + 1])

list_d = list(d1.items())
print(list_d)
list_d.sort(key=lambda i: i[1],reverse=True)
for i in list_d:
    print(i[0], ':', i[1])

#print(cos(matrix.todense()[0], matrix.todense()[i + 1]), 'vk.com/id' + strings[i])

# sum = 0.0
# for i in range(matrix.getrow(1).shape[1]):
#     sum += matrix.getrow(1).getcol(i) * matrix.getrow(0).getcol(i)
# print(sum)
#
# normalize1 = 0.0
# normalize2 = 0.0
# for i in range(matrix.getrow(1).shape[1]):
#     normalize1 += matrix.getrow(0).getcol(i) * matrix.getrow(0).getcol(i)
#
# for i in range(matrix.getrow(1).shape[1]):
#     normalize2 += matrix.getrow(1).getcol(i) * matrix.getrow(1).getcol(i)
#
# normalize1 = sqrt(normalize1)
# print(normalize1)
# normalize2 = sqrt(normalize2)
# print(normalize2)
#
# cos = sum/(normalize1 * normalize2)
#
# print(cos)