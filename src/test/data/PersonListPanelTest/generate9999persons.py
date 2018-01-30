f = open('9999persons.xml','w')

f.write('<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n')
f.write('<addressbook>\n')
for i in range(9999):
    f.write('<persons>\n')
    f.write('<name>{}</name>\n'.format(''.join('ABCDEFGHIJ'[int(x)] for x in str(i))))
    f.write('<phone>123</phone>\n')
    f.write('<email>a@a</email>\n')
    f.write('<address>a</address>\n')
    f.write('</persons>\n')
f.write('</addressbook>\n')
