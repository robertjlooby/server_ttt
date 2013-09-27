require 'jasmine'
load 'jasmine/tasks/jasmine.rake'

task :source_watch do
  s = "coffee -o #{__dir__}/ttt_public/javascripts/ -cw #{__dir__}/src/coffeescripts/"
  puts s
  exec s
end

task :spec_watch do
  s = "coffee -cw #{__dir__}/spec/javascripts/"
  puts s
  exec s
end

task :spec_trim do
  require 'tempfile'
  fname = "#{__dir__}/spec/javascripts/mainSpec.js"
  mtime = File.mtime(fname)
  
  loop do
    if File.mtime(fname) != mtime
      temp = Tempfile.new('spec')
      flag = false
      good_line = "goog.provide(\"abstract_display\");\n"
      File.open(fname, 'r').each_line do |line|
        flag = true if line == good_line
        temp.write(line) if flag
      end
      temp.rewind
      IO.copy_stream(temp, fname)
      temp.close
      temp.unlink
      puts "trimmed google closure code from #{fname}"
      mtime = File.mtime(fname)
    else
      sleep(1)
    end
  end
end
